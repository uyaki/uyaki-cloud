package com.uyaba.cloud.common.core.enums.neo4j;

/**
 * neo4j查询统计类型
 *
 * @author noone
 * @date 2019-07-01 14:48
 */
public enum Neo4jQueryStatsTypeEnum {
    /*
    节点扩展
     */
    NODE_EXPAND("节点扩展查询"),
    /*
     两节点间全量路径查询
     */
    TWO_NODES_PATHS("两节点间全量路径查询"),
    /*
     两节点间最短路径查询
     */
    TWO_NODES_SHORTEST_PATHS("两节点间最短路径查询");

    private String value;

    Neo4jQueryStatsTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
