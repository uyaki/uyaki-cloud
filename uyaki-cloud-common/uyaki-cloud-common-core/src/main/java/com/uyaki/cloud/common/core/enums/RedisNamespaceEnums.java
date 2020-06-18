package com.uyaki.cloud.common.core.enums;

/**
 * Redis名字空间
 *
 * @author noone
 * @date 2019-07-03 18:24
 */
public enum RedisNamespaceEnums {
    /**
     节点标签
     */
    NEO4J_NODE_LABELS("neo4j_node_labels"),
    /**
     关系类型
     */
    NEO4J_RELATIONSHIP_TYPES("neo4j_relationship_types"),
    /**
     属性关键字
     */
    NEO4J_PROPERTY_KEYS("neo4j_property_keys");
    private String value;
    RedisNamespaceEnums(String value){
        this.value=value;
    }
    public String value(){
        return value;
    }
}
