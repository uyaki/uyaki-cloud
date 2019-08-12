package com.gknoone.cloud.plus.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.filter.LabelFilter;
import com.gknoone.cloud.plus.common.core.filter.RelationshipFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JSON工具类
 *
 * @author noone
 * @date 2019-04-29 18:41
 */
public class CypherUtil {

    /**
     * 将val值解析成对应的数据类型字符串<br>
     * 其中字符串加双引号
     *
     * @param val val
     */
    private static String parseRealDataTypeStringFromObject(Object val) {
        if (val instanceof Number || val instanceof Boolean) {
            return val.toString();
        } else if (val instanceof List) {
            JSONArray list = (JSONArray) val;
            StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
            for (Object item : list) {
                if (item instanceof Number) {
                    stringJoiner.add(item.toString());
                } else {
                    stringJoiner.add(String.format("\"%s\"", item));
                }
            }
            return stringJoiner.toString();
        } else {
            return String.format("\"%s\"", val);
        }
    }

    /**
     * 获取键值对解析结果
     *
     * @param jsonString 键值对json
     * @param format     键值对解析模板
     * @param prefix     封装前缀
     * @param suffix     封装后缀
     * @return 解析结果：prefix + format + suffix e.g. {key:value}
     */
    private static String parseKeyValueCypher(String jsonString,
                                              String format,
                                              String prefix,
                                              String suffix) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        JSONObject myJson = JSONObject.parseObject(jsonString);
        StringJoiner stringJoiner = new StringJoiner(",", prefix, suffix);
        for (Map.Entry<String, Object> entry : myJson.entrySet()) {
            String valStr = parseRealDataTypeStringFromObject(entry.getValue());
            stringJoiner.add(String.format(format, entry.getKey(), valStr));
        }
        return stringJoiner.toString();
    }


    /**
     * 根据指定的key解析json键值对
     *
     * @param json          json
     * @param specifiedKeys 指定的key
     * @return 键值对字符串
     */
    private static String parseJsonStringBySpecifiedKey(JSONObject json, String[] specifiedKeys) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String key : specifiedKeys) {
            stringJoiner.add(String.format("%s:%s", key, json.get(key)));
        }
        return stringJoiner.toString();
    }

    /**
     * 唯一约束属性语句
     *
     * @param keyValueJsonStr 键值对字符串
     * @param uniqueKeys     唯一项
     * @return cypher
     */
    private static String getUniquePropertyCypher(String keyValueJsonStr, String[] uniqueKeys) {
        if (StringUtils.isEmpty(keyValueJsonStr)) {
            return null;
        }
        JSONObject myJson = JSONObject.parseObject(keyValueJsonStr);
        String sb = parseJsonStringBySpecifiedKey(myJson, uniqueKeys);
        return String.format("{%s}", sb);
    }


    /**
     * 获取节点创建语句
     *
     * @param nodeLabels         节点标签
     * @param propertiesJson 节点属性（虽然neo4j允许创建无属性节点，但是后台设计不允许）
     * @return 节点创建语句
     */
    public static String getNodeCreateCypher(String[] nodeLabels, String propertiesJson) {
        String propertiesCypher = CypherUtil.parseKeyValueCypher(propertiesJson, "%s:%s", "{", "}");
        StringBuffer sb = new StringBuffer();
        Arrays.stream(nodeLabels).forEach(it -> sb.append(":").append(it));
        return String.format("CREATE (n %s %s) RETURN n", sb, propertiesCypher);
    }

    /**
     * 获取节点Merge创建语句
     *
     * @param nodeLabels         节点标签
     * @param propertiesJson 节点属性
     * @param uniqueKeys    唯一键
     * @return 节点创建语句
     */
    public static String getMergeNodeCypher(String[] nodeLabels, String propertiesJson, String[] uniqueKeys) {
        StringBuffer sb = new StringBuffer();
        Arrays.stream(nodeLabels).forEach(it -> sb.append(":").append(it));
        String propertiesCypher = CypherUtil.parseKeyValueCypher(propertiesJson, "%s:%s", "{", "}");
        String uniqueKeyCypher = CypherUtil.getUniquePropertyCypher(propertiesJson, uniqueKeys);
        return String.format("MERGE (n%s %s)\n" +
                "SET n += %s \n" +
                "RETURN n", sb, uniqueKeyCypher, propertiesCypher);
    }

    /**
     * 获取节点Merge创建语句
     *
     * @param nodeLabel               节点标签
     * @param propertiesJsonArray 节点属性
     * @param uniqueKeys               唯一键
     * @return 节点创建语句
     */
    public static String getMergeNodesCypher(String nodeLabel, String[] propertiesJsonArray, String[] uniqueKeys) {
        String batch = parseBatchForUnwind(propertiesJsonArray);
        String mergeCypherUniquePart = getMergeCypherUniquePart(uniqueKeys);
        String mergeCypherSetPart = getMergeCypherSetPart(propertiesJsonArray);
        return String.format("UNWIND %s as row\n" +
                "merge (n:%s %s)\n" +
                "SET %s\n" +
                "return n", batch, nodeLabel, mergeCypherUniquePart, mergeCypherSetPart);
    }

    /**
     * 获取增量节点与关系添加cql
     * @param startNodeId 开始节点id
     * @param startNodeLabel 开始节点标签
     * @param startNodeUniqueProperties 开始节点唯一属性
     * @param mergeNodeLabel 增量节点标签
     * @param mergeNodePropertiesJsonArrays 增量节点属性数组
     * @param mergeRelationshipType 增量关系类型
     * @param mergeRelationshipPropertiesJson 增量关系属性
     * @return 增量节点与关系添加cql
     */
    public static String getMergeNodesAndRelationCypher(String startNodeId,
                                                        String startNodeLabel,
                                                        String[] startNodeUniqueProperties,
                                                        String mergeNodeLabel,
                                                        String[] mergeNodePropertiesJsonArrays,
                                                        String mergeRelationshipType,
                                                        String mergeRelationshipPropertiesJson) {
        String batch = parseBatchForUnwind(mergeNodePropertiesJsonArrays);
        String mergeNodeCypherUniquePart;
        if (ArrayUtils.isEmpty(startNodeUniqueProperties)) {
            String[] temp = {"name"};
            mergeNodeCypherUniquePart = getMergeCypherUniquePart(temp);
        } else {
            mergeNodeCypherUniquePart = getMergeCypherUniquePart(startNodeUniqueProperties);
        }
        String mergeNodeCypherSetPart = getMergeCypherSetPart(mergeNodePropertiesJsonArrays);
        String mergeRelationshipCypher = parseKeyValueCypher(mergeRelationshipPropertiesJson, "%s:%s", "{", "}");
        return String.format("UNWIND %s as row\n" +
                        "match (s:%s)\n" +
                        "where id(s)=%s\n" +
                        "merge (n:%s %s)\n" +
                        "SET %s\n" +
                        "merge (s)-[r:%s%s]-> (n)\n" +
                        "return s,r,n",
                batch, startNodeLabel, startNodeId, mergeNodeLabel,
                mergeNodeCypherUniquePart, mergeNodeCypherSetPart,
                mergeRelationshipType, StringUtils.isNotEmpty(mergeRelationshipCypher) ? mergeRelationshipCypher : "");
    }

    /**
     * 获取Set部分语句
     *
     * @param propertiesJsonArray 节点属性
     * @return Set语句
     */
    private static String getMergeCypherSetPart(String[] propertiesJsonArray) {
        Set<String> set = new HashSet<>();
        for (String propertiesJson : propertiesJsonArray) {
            if (StringUtils.isNotEmpty(propertiesJson)) {
                JSONObject jsonObject = JSON.parseObject(propertiesJson);
                set.addAll(jsonObject.keySet());
            }
        }
        StringJoiner stringJoiner = new StringJoiner(",");
        set.forEach(it -> stringJoiner.add(String.format("n.%s = row.%s", it, it)));
        return stringJoiner.toString();
    }

    /**
     * 获取唯一键语句
     *
     * @param uniqueKeys 唯一键
     * @return cypher
     */
    private static String getMergeCypherUniquePart(String[] uniqueKeys) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String uniqueKey : uniqueKeys) {
            stringJoiner.add(String.format("%s:row.%s", uniqueKey, uniqueKey));
        }
        return String.format("{%s}", stringJoiner);
    }

    /**
     * 解析batch数据用于Unwind
     *
     * @param propertiesJsonArrays 数据
     * @return batch
     */
    private static String parseBatchForUnwind(String[] propertiesJsonArrays) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String propertiesJson : propertiesJsonArrays) {
            if (StringUtils.isNotEmpty(propertiesJson)) {
                stringJoiner.add(parseKeyValueCypher(propertiesJson, "%s:%s", "{", "}"));
            }
        }
        return String.format("[%s]", stringJoiner);
    }

    /**
     * 获取节点更新语句
     *
     * @param id               节点id
     * @param addNodeLabels        节点新增标签
     * @param propertiesJson   节点属性
     * @param removeLabels     节点需要移除的标签
     * @param removePropertyKeys 节点需要移除的属性
     * @return 节点更新语句
     */
    public static String getNodeUpdateCypher(Long id,
                                             String[] addNodeLabels,
                                             String propertiesJson,
                                             String[] removeLabels,
                                             String[] removePropertyKeys) {
        boolean addLabelIsEmpty = ArrayUtils.isEmpty(addNodeLabels);
        boolean propertiesJsonIsEmpty = StringUtils.isEmpty(propertiesJson);
        boolean removeLabelsIsEmpty = ArrayUtils.isEmpty(removeLabels);
        boolean removePropertiesIsEmpty = ArrayUtils.isEmpty(removePropertyKeys);
        //属性和标签不能同时为空
        if (addLabelIsEmpty && propertiesJsonIsEmpty && removeLabelsIsEmpty && removePropertiesIsEmpty) {
            return null;
        }
        // 获取keyValue语句
        String propertiesCypher = CypherUtil.parseKeyValueCypher(propertiesJson, "n.%s = %s", "", "");
        boolean propertiesCypherIsEmpty = StringUtils.isEmpty(propertiesCypher);


        //只要有一个非空，就可以开始SET操作

        StringBuffer setPart = new StringBuffer();
        if (!addLabelIsEmpty || !propertiesCypherIsEmpty) {
            setPart.append(" SET ");
            if (!addLabelIsEmpty) {
                Arrays.stream(addNodeLabels).forEach(it -> setPart.append("n:").append(it).append(","));
                setPart.deleteCharAt(setPart.length() - 1);
            }
            setPart.append(!addLabelIsEmpty && !propertiesCypherIsEmpty ? "," : "")
                    .append(!propertiesCypherIsEmpty ? propertiesCypher : "");
        }
        StringBuffer removePart = new StringBuffer();
        // 开始REMOVE操作
        if (!removeLabelsIsEmpty || !removePropertiesIsEmpty) {
            removePart.append(" REMOVE ");
            if (!removeLabelsIsEmpty) {
                Arrays.stream(removeLabels).forEach(it -> removePart.append("n:").append(it).append(","));
                removePart.deleteCharAt(removePart.length() - 1);
            }
            removePart.append(!removeLabelsIsEmpty && !removePropertiesIsEmpty ? "," : "");
            if (!removePropertiesIsEmpty) {
                Arrays.stream(removePropertyKeys).forEach(it -> removePart.append("n.").append(it).append(","));
                removePart.deleteCharAt(removePart.length() - 1);
            }
        }
        return String.format("MATCH (n) \n" +
                " WHERE id(n) = %s \n" +
                "%s \n" +
                "%s \n" +
                "RETURN n", id, setPart, removePart);
    }

    /**
     * 指定两个节点间的关系创建Cql
     *
     * @param startNodeId                开始节点id
     * @param endNodeId                  结束节点id
     * @param relationshipType          关系标签
     * @param relationshipPropertiesJson 关系属性
     * @return cql
     */
    public static String getExclusiveRelationshipCreateCypher(Long startNodeId,
                                                              Long endNodeId,
                                                              String relationshipType,
                                                              String relationshipPropertiesJson) {
        String propertiesCypher = CypherUtil.parseKeyValueCypher(relationshipPropertiesJson, "%s:%s", "{", "}");
        return String.format("MATCH (s),(e) \n" +
                        " WHERE id(s) = %s AND id(e) = %s \n" +
                        " CREATE (s)-[r:%s %s ]->(e) \n" +
                        " RETURN s,r,e",
                startNodeId, endNodeId, relationshipType, StringUtils.isNotEmpty(propertiesCypher) ? propertiesCypher : "");

    }

    /**
     * 获取关系更新语句
     *
     * @param id               id
     * @param propertiesJson   关系属性
     * @param removePropertyKeys 移除属性关键字
     * @return cql
     */
    public static String getRelationshipUpdateCypher(Long id, String propertiesJson, String[] removePropertyKeys) {
        boolean propertiesJsonIsEmpty = StringUtils.isEmpty(propertiesJson);
        boolean removePropertiesIsEmpty = ArrayUtils.isEmpty(removePropertyKeys);
        //属性和标签不能同时为空
        if (propertiesJsonIsEmpty && removePropertiesIsEmpty) {
            return null;
        }
        // 获取keyValue语句
        String propertiesCypher = CypherUtil.parseKeyValueCypher(propertiesJson, "n.%s = %s", "", "");
        boolean propertiesCypherIsEmpty = StringUtils.isEmpty(propertiesCypher);
        //开始SET操作
        StringBuffer setPart = new StringBuffer();
        if (!propertiesCypherIsEmpty) {
            setPart.append(" SET ");
            setPart.append(propertiesCypher.replaceAll("n\\.", "r."));
        }
        StringBuffer removePart = new StringBuffer();
        // 开始REMOVE操作
        if (!removePropertiesIsEmpty) {
            removePart.append(" REMOVE ");
            Arrays.stream(removePropertyKeys).forEach(it -> removePart.append("r.").append(it).append(","));
            removePart.deleteCharAt(removePart.length() - 1);
        }
        return String.format("MATCH p=()-[r]->() \n" +
                        " WHERE id(r) = %s\n" +
                        " %s \n" +
                        " %s \n" +
                        "RETURN p",
                id, setPart, removePart);
    }

    /**
     * 获取节点删除语句
     *
     * @param id id
     * @return 节点删除语句
     */
    public static String getNodeDeleteCypher(Long id) {
        return String.format("MATCH (n)-[r]-() WHERE id(n) = %s DELETE n,r", id);
    }

    /**
     * 获取关系删除语句
     *
     * @param id id
     * @return 关系删除语句
     */
    public static String getRelationshipDeleteCypher(Long id) {
        return String.format("MATCH ()-[r]-() WHERE id(r) = %s DELETE r", id);
    }

    /**
     * 获取节点扩展路径
     *
     * @param id                  id
     * @param relationshipFilters 关系过滤条件
     * @param labelFilters        标签过滤条件
     * @param minLevel            最小层级
     * @param maxLevel            最大层级
     * @return 路径
     */
    public static String getCallApocPathExpandCypher(Long id, List<RelationshipFilter> relationshipFilters, List<LabelFilter> labelFilters, int minLevel, int maxLevel) {
        String relationshipFilter = relationshipFilters != null ? relationshipFilters.stream().map(RelationshipFilter::toString).collect(Collectors.joining("|")) : "";
        String labelFilter = labelFilters != null ? labelFilters.stream().map(LabelFilter::toString).collect(Collectors.joining("|")) : "";
        return String.format("CALL apoc.path.expand(%s, '%s','%s', %s, %s ) YIELD path RETURN path", id, relationshipFilter, labelFilter, minLevel, maxLevel);
    }

    /**
     * 2节点间全路径检索
     *
     * @param startNodeId 开始节点Id
     * @param endNodeId   截止节点Id
     * @param directed    是否有向
     * @param dept        深度
     * @return 路径
     */
    public static String getAllPathBetween2NodeCypher(Long startNodeId, Long endNodeId, boolean directed, int dept) {
        return String.format("MATCH p=(n1)-[*..%s]-%s(n2)\n" +
                        " WHERE id(n1) = %s and id(n2) = %s\n" +
                        " RETURN p",
                dept, directed ? MathSymbolEnum.GREATER_THAN.symbol() : "", startNodeId, endNodeId);
    }

    /**
     * 获取两节点的最短路径
     *
     * @param startNodeId 开始节点Id
     * @param endNodeId   截止节点Id
     * @param directed    是否有向
     * @param dept        深度
     * @return 路径
     */
    public static String getAllShortestPathsBetween2NodeCypher(Long startNodeId, Long endNodeId, boolean directed, int dept) {
        return String.format("MATCH p=allshortestpaths((n1)-[*..%s]-%s(n2))\n" +
                        " WHERE id(n1) = %s and id(n2) = %s\n" +
                        " RETURN p",
                dept, directed ? MathSymbolEnum.GREATER_THAN.symbol() : "", startNodeId, endNodeId);
    }

    /**
     * 根据节点标签获取节点数量
     * @param nodeLabel 节点标签
     * @return 数量
     */
    public static String getNodeCountByNodeLabel (String nodeLabel) {
        return String.format("MATCH (n:%s)  RETURN count(*)", nodeLabel);
    }

    /**
     * 根据关系类型获取路径数量
     * @param relationshipType 关系类型
     * @return 数量
     */
    public static String getCountByRelationshipType(String relationshipType) {
        return String.format("MATCH p=()-[r:%s]->() RETURN count(*)",relationshipType);
    }
    /**
     * 获取节点模糊查询数量统计cql
     *
     * @param nodeLabels          节点标签名
     * @param propertyKey 属性关键字名
     * @param keyword         模糊关键字
     * @return cql
     */
    public static String getNodeFuzzySearchCount(String[] nodeLabels, String propertyKey, String keyword) {
        String nodeFuzzySearchPrefix = getNodeFuzzySearchPrefix(nodeLabels, propertyKey, keyword);
        return String.format("%s \n" +
                " RETURN count(*)", nodeFuzzySearchPrefix);
    }

    /**
     * 节点模糊查询前缀cql
     *
     * @param nodeLabels          节点标签名
     * @param propertyKey 属性关键字名
     * @param keyword         模糊关键字
     * @return 前缀cql
     */
    private static String getNodeFuzzySearchPrefix(String[] nodeLabels, String propertyKey, String keyword) {
        StringBuffer labelPart = new StringBuffer();
        if (ArrayUtils.isNotEmpty(nodeLabels)) {
            Arrays.stream(nodeLabels).forEach(it -> labelPart.append(":").append(it));
        }
        if (StringUtils.isEmpty(propertyKey) || StringUtils.isEmpty(keyword)) {
            return String.format("MATCH (n %s) \n", labelPart);
        } else {
            return String.format("MATCH (n %s) \n" +
                    " WHERE n.%s =~ \".*%s.*\"", labelPart, propertyKey, keyword);
        }
    }

    /**
     * 根据节点标签查询节点的cql语句
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param nodeLabel 节点标签
     * @return 节点
     */
    public static String getNodeByNodeLabelCypher(Integer pageNum, Integer pageSize, String nodeLabel) {
        return String.format("MATCH (n:%s) RETURN n  SKIP %s LIMIT %s", nodeLabel, (pageNum - 1) * pageSize, pageSize);
    }

    /**
     * 根据关系类型获取路径
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param relationshipType 关系类型
     * @return cql
     */
    public static String getPathByRelationshipType(Integer pageNum, Integer pageSize, String relationshipType) {
        return String.format("MATCH p=()-[r:%s]->() RETURN p SKIP %s  LIMIT %s",relationshipType, (pageNum - 1) * pageSize, pageSize);
    }
    /**
     * 根据标签和属性，模糊查询节点
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param nodeLabels 节点标签
     * @param propertyKeyName 属性
     * @param keyword 关键字
     * @return 节点
     */
    public static String getNodeFuzzySearchCypher(Integer pageNum, Integer pageSize, String[] nodeLabels, String propertyKeyName, String keyword) {
        String nodeFuzzySearchPrefix = getNodeFuzzySearchPrefix(nodeLabels, propertyKeyName, keyword);
        return String.format("%s \n" +
                " RETURN n \n" +
                " ORDER BY id(n) ASC \n" +
                " SKIP %s LIMIT %s", nodeFuzzySearchPrefix, (pageNum - 1) * pageSize, pageSize);

    }

    /**
     * 获取唯一约束创建cypher
     *
     * @param nodeLabel   节点标签
     * @param propertyKey 属性关键字
     * @return cypher
     */
    public static String getCreateConstraintUnique(String nodeLabel, String propertyKey) {
        return String.format("CREATE CONSTRAINT ON (n:%s) ASSERT n.%s IS UNIQUE", nodeLabel, propertyKey);
    }

    /**
     * 获取唯一约束删除cypher
     *
     * @param nodeLabel   节点标签
     * @param propertyKey 属性关键字
     * @return cypher
     */
    public static String getDropConstraintUnique(String nodeLabel, String propertyKey) {
        return String.format("DROP CONSTRAINT ON (n:%s) ASSERT n.%s IS UNIQUE", nodeLabel, propertyKey);
    }

    public static String getCheckNodeIsExist(Long id, String nodeLabel, String propertyJson) {

        String update = id == null ? "" : String.format("WHERE id(n) <> %s", id);
        String property = parseKeyValueCypher(propertyJson, "%s:%s", "{", "}");
        return String.format("MATCH (n:%s %s) " +
                " %s" +
                " RETURN count(*)", nodeLabel, property, update);
    }
}
