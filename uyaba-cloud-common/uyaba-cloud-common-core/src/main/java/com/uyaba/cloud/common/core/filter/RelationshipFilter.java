package com.uyaba.cloud.common.core.filter;

import com.uyaba.cloud.common.core.enums.neo4j.Neo4jRelationShipFilterDirectionEnum;
import lombok.Data;

/**
 * 关系过滤器
 *
 * @author noone
 * @date 2019-05-14 16:30
 */
@Data
public class RelationshipFilter {

    private String name;
    private String direction;

    @Override
    public String toString() {
        return  name + Neo4jRelationShipFilterDirectionEnum.value(direction);
    }
}
