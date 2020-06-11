package com.uyaba.cloud.common.core.filter;

import com.uyaba.cloud.common.core.enums.neo4j.Neo4jLabelCategoryFilterEnum;
import lombok.Data;

/**
 * 节点标签过滤器
 *
 * @author noone
 * @date 2019-05-14 16:36
 */
@Data
public class LabelFilter {
    private String name;
    private String category;

    @Override
    public String toString() {
        return  Neo4jLabelCategoryFilterEnum.value(category)+name ;
    }
}
