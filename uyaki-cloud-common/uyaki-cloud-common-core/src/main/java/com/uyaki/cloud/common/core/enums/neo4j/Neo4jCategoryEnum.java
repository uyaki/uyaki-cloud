package com.uyaki.cloud.common.core.enums.neo4j;

/**
 * neo4j类别枚举
 *
 * @author noone
 * @date 2019-05-10 16:32
 */
public enum Neo4jCategoryEnum {
    /**
     * 节点标签
     */
    NODE_LABELS("node_labels"),
    /**
     * 关系类型
     */
    RELATIONSHIP_TYPES("relationship_types"),
    /**
     * 属性关键字
     */
    PROPERTY_KEYS("property_keys");
    private String value;

    Neo4jCategoryEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
