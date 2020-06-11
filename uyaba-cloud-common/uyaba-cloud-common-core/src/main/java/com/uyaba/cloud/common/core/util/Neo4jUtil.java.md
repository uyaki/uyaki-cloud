package com.uyaba.cloud.common.core.util;

import com.google.common.collect.Lists;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * neo4j工具类
 *
 * @author noone
 * @date 2019-04-24 09:30
 */
@Component
public class Neo4jUtil {
    private Logger logger = LoggerFactory.getLogger(Neo4jUtil.class);

    @Autowired
    private Driver neo4jDriver;

    /**
     * neo4j是否已经打开
     *
     * @return boolean
     */
    public boolean isNeo4jOpen() {
        try (Session session = neo4jDriver.session()) {
            logger.info("连接成功：{}", session.isOpen());
            return session.isOpen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 执行CQL语句
     *
     * @param cql cql
     * @return 执行结果
     */
    public StatementResult executeCQL(String cql) {
        StatementResult statementResult = null;
        try (Session session = neo4jDriver.session()) {
            logger.info(cql);
            statementResult = session.run(cql);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statementResult;
    }

    /**
     * 获取实体（节点）的Map【适用于单个节点新增修改删除的场景】
     *
     * @param cql cypher查询语句
     * @return 节点Map
     */
    public HashMap<String, Object> getEntityMap(String cql) {
        HashMap<String, Object> result = new HashMap<String, Object>(3);
//        HashMap<String, Object> temp = new HashMap<>(1);
        try {
            StatementResult statementResult = executeCQL(cql);
            if (statementResult.hasNext()) {
                List<Record> records = statementResult.list();
                for (Record recordItem : records) {
                    for (Value value : recordItem.values()) {
                        // 结果里面只要类型为节点的值
                        if ("NODE".equals(value.type().name())) {
                            Node neo4jNode = value.asNode();
                            Map<String, Object> neo4jNodeMap = neo4jNode.asMap();
//                            for (Map.Entry<String, Object> entry : neo4jNodeMap.entrySet()) {
//                                String key = entry.getKey();
//                                if (temp.containsKey(key)) {
//                                    String oldValue = temp.get(key).toString();
//                                    String newValue = oldValue + "," + entry.value();
//                                    temp.replace(key, newValue);
//                                } else {
//                                    temp.put(key, entry.value());
//                                }
//                            }
                            Long id = neo4jNode.id();
                            result.put("id", id);
                            result.put("properties", neo4jNodeMap);
                            List<String> labelList = Lists.newArrayList(neo4jNode.labels().iterator());
                            result.put("labels", labelList);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析节点关系图
     *
     * @param cql cql
     * @return 结果
     */
    public HashMap<String, Object> getGraphNodeAndShip(String cql) {
        HashMap<String, Object> result = new HashMap<>(2);
        try {
            StatementResult statementResult = executeCQL(cql);
            if (statementResult.hasNext()) {
                List<Record> records = statementResult.list();
                // 存放去重后的节点
                List<HashMap<String, Object>> entitiesResult = new ArrayList<HashMap<String, Object>>();
                // 存放去重后的关系
                List<HashMap<String, Object>> shipsResult = new ArrayList<HashMap<String, Object>>();
                // 去重唯一标识
                List<Long> nodeIds = new ArrayList<>();
                List<Long> shipIds = new ArrayList<>();
                for (Record recordItem : records) {
                    //获取键值对
                    List<Pair<String, Value>> f = recordItem.fields();
                    for (Pair<String, Value> pair : f) {
                        HashMap<String, Object> nodeMap = new HashMap<String, Object>(10);
                        String typeName = pair.value().type().name();
                        if ("NULL".equals(typeName)) {
                            continue;
                        } else if ("NODE".equals(typeName)) {
                            Node neo4jNode = pair.value().asNode();
                            analysisNodeEntities(entitiesResult, nodeIds, neo4jNode);
                        } else if ("RELATIONSHIP".equals(typeName)) {
                            Relationship relationship = pair.value().asRelationship();
                            analysisRelationship(shipsResult, shipIds, relationship);
                        } else if ("PATH".equals(typeName)) {
                            Path path = pair.value().asPath();
                            //解析路径的开始节点
                            Node startNode = path.start();
                            analysisNodeEntities(entitiesResult, nodeIds, startNode);
                            //解析路径的结束节点
                            Node endNode = path.end();
                            analysisNodeEntities(entitiesResult, nodeIds, endNode);
                            //解析路径的关系
                            for (Relationship relationship : path.relationships()) {
                                analysisRelationship(shipsResult, shipIds, relationship);
                            }
                        } else if (typeName.contains("LIST")) {
                            Iterable<Value> val = pair.value().values();
                            Value next = val.iterator().next();
                            String type = next.type().name();
                            if ("RELATIONSHIP".equals(type)) {
                                Relationship relationship = next.asRelationship();
                                analysisRelationship(shipsResult, shipIds, relationship);
                            }
                        } else if (typeName.contains("MAP")) {
                            nodeMap.put(pair.key(), pair.value().asMap());
                        } else {
                            nodeMap.put(pair.key(), pair.value().toString());
                            if (!nodeMap.isEmpty()) {
                                entitiesResult.add(nodeMap);
                            }
                        }
                    }
                }
                result.put("node", entitiesResult);
                result.put("relationship", shipsResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析关系
     *
     * @param shipsResult  关系集合
     * @param shipIds      关系去重list
     * @param relationship 待解析关系实体
     */
    private void analysisRelationship(List<HashMap<String, Object>> shipsResult, List<Long> shipIds, Relationship relationship) {
        HashMap<String, Object> tempShipMap = new HashMap<String, Object>(10);
        Long relationshipId = relationship.id();
        if (!shipIds.contains(relationshipId)) {
            Long sourceUid = relationship.startNodeId();
            Long targetUid = relationship.endNodeId();
            Map<String, Object> shipMap = relationship.asMap();
            for (Map.Entry<String, Object> entry : shipMap.entrySet()) {
                String key = entry.getKey();
                tempShipMap.put(key, entry.getValue());
            }
            tempShipMap.put("id", relationshipId);
            tempShipMap.put("sourceId", sourceUid);
            tempShipMap.put("targetId", targetUid);
            if (!tempShipMap.isEmpty()) {
                shipsResult.add(tempShipMap);
            }
        }
    }

    /**
     * 解析节点
     *
     * @param entitiesResult 节点总集合
     * @param nodeIds        节点去重list
     * @param neo4jNode      待解析节点
     */
    private void analysisNodeEntities(List<HashMap<String, Object>> entitiesResult, List<Long> nodeIds, Node neo4jNode) {
        //存放节点的Map
        HashMap<String, Object> tempNodeMap = new HashMap<>(2);
        //存放属性解析的Map
        Map<String, Object> nodeMap = neo4jNode.asMap();
        Long nodeId = neo4jNode.id();
        //防止重复录入
        if (!nodeIds.contains(nodeId)) {
            tempNodeMap.put("id", nodeId);
            tempNodeMap.put("properties", nodeMap);
            tempNodeMap.put("labels", Lists.newArrayList(neo4jNode.labels().iterator()));
            // 新增过滤名单
            nodeIds.add(nodeId);
            if (!tempNodeMap.isEmpty()) {
                entitiesResult.add(tempNodeMap);
            }
        }
    }

    /**
     * 查询路径，解析路径结果
     *
     * @param cql cql
     * @return 结果
     */

    public HashMap<String, Object> getPath(String cql) {
        //结果存放
        HashMap<String, Object> result = new HashMap<>(2);
        try {
            StatementResult statementResult = executeCQL(cql);
            if (statementResult.hasNext()) {
                List<Record> records = statementResult.list();
                // 存放去重后的节点
                List<HashMap<String, Object>> entitiesResult = new ArrayList<HashMap<String, Object>>(10);
                // 存放去重后的关系
                List<HashMap<String, Object>> shipsResult = new ArrayList<HashMap<String, Object>>(10);
                // 去重唯一标识
                List<Long> nodeIds = new ArrayList<>();
                List<Long> shipIds = new ArrayList<>();
                for (Record recordItem : records) {
                    //获取键值对
                    List<Value> values = recordItem.values();
                    for (Value value : values) {
                        if ("PATH".equalsIgnoreCase(value.type().name())) {
                            Path p = value.asPath();
                            Iterable<Node> nodes = p.nodes();
                            for (Node node : nodes) {
                                analysisNodeEntities(entitiesResult, nodeIds, node);
                            }
                            Iterable<Relationship> relationships = p.relationships();
                            for (Relationship relationship : relationships) {
                                analysisRelationship(shipsResult, shipIds, relationship);
                            }

                        }
                    }
                }
                result.put("node", entitiesResult);
                result.put("relationship", shipsResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long getCount(String cql) {
        AtomicLong result = new AtomicLong();
        try {
            StatementResult statementResult = executeCQL(cql);
            if (statementResult.hasNext()) {
               Record record = statementResult.next();
              result.set(record.get("count(*)").asLong());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get();
    }

    /**
     * 获取实体（节点）的Map【适用于多个node】
     *
     * @param cql cypher查询语句
     * @return 节点Map
     */
    public List<HashMap<String, Object>> getEntityMapList(String cql) {
        List<HashMap<String,Object>> result = new ArrayList<>();
        try {
            StatementResult statementResult = executeCQL(cql);
            if (statementResult.hasNext()) {
                List<Record> records = statementResult.list();
                for (Record recordItem : records) {
                    for (Value value : recordItem.values()) {
                        // 结果里面只要类型为节点的值
                        if ("NODE".equals(value.type().name())) {
                            Node neo4jNode = value.asNode();
                            Map<String, Object> neo4jNodeMap = neo4jNode.asMap();
                            HashMap<String,Object> temp = new HashMap<>(3);
                            Long id = neo4jNode.id();
                            temp.put("id", id);
                            temp.put("properties", neo4jNodeMap);
                            List<String> labelList = Lists.newArrayList(neo4jNode.labels().iterator());
                            temp.put("labels", labelList);
                            result.add(temp);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
