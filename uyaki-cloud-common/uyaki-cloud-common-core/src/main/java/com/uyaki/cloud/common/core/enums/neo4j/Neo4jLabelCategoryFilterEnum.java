package com.uyaki.cloud.common.core.enums.neo4j;

/**
 * 标签过滤类别枚举
 *
 * @author noone
 * @date 2019-05-14 17:11
 */
public enum Neo4jLabelCategoryFilterEnum {
    /**
     * 排除
     */
    EXCLUDE("exclude","-"),
    /**
     * 包含
     */
    INCLUDE("include","+"),
    /**
     * 终止并返回 （遍历路径直到遇见该类型的节点，然后仅返回该节点。
     */
    STOP_AND_RETURN("stop_and_return","/"),
    /**
     * 遍历路径只返回到达该类型的节点(含)之前的部分，该
     * 节点之后的部分会继续被遍历，但是不会被返回。
     */
    STOP_BUT_CONTINUE("stop_but_continue",">");
    private String key;
    private String value;

    Neo4jLabelCategoryFilterEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String value(String key) {
        Neo4jLabelCategoryFilterEnum[] neo4jLabelCategoryFilterEnums = values();
        for (Neo4jLabelCategoryFilterEnum neo4jLabelCategoryFilterEnum : neo4jLabelCategoryFilterEnums) {
            if (neo4jLabelCategoryFilterEnum.key().equals(key)) {
                return neo4jLabelCategoryFilterEnum.value();
            }
        }
        return null;
    }
    public String key() {
        return key;
    }



    public String value() {
        return value;
    }
}
