package com.gknoone.cloud.plus.common.core.enums.neo4j;

/**
 * 关系方向枚举
 *
 * @author noone
 * @date 2019-05-14 16:16
 */
public enum Neo4jRelationShipFilterDirectionEnum {
    /**
     * 进
     */
    IN("in","<"),
    /**
     * 出
     */
    OUT("out",">"),
    /**
     * 双向
     */
    BI_DIRECTIONAL("bi_directional","");

    private String key;
    private String value;

    Neo4jRelationShipFilterDirectionEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String value(String key) {
        Neo4jRelationShipFilterDirectionEnum[] neo4JRelationShipFilterDirectionEnums = values();
        for (Neo4jRelationShipFilterDirectionEnum neo4JRelationShipFilterDirectionEnum : neo4JRelationShipFilterDirectionEnums) {
            if (neo4JRelationShipFilterDirectionEnum.key().equals(key)) {
                return neo4JRelationShipFilterDirectionEnum.value();
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
