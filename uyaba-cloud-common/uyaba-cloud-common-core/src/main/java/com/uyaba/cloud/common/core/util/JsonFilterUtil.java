package com.uyaba.cloud.common.core.util;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.pagehelper.PageInfo;

/**
 * JSON过滤器工具类
 *
 * @author noone
 * @date 2019-05-09 18:21
 */
public class JsonFilterUtil {
    /**
     * 获取分页过滤器
     *
     * @return 过滤器
     */
    public static SimplePropertyPreFilter getPageFilter() {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(PageInfo.class);
        filter.getIncludes().add("list");
        filter.getIncludes().add("total");
        filter.getIncludes().add("pages");
        return filter;
    }

    /**
     * 获取树过滤器
     *
     * @return 过滤器
     */
    public static SimplePropertyPreFilter getTreeFilter() {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().add("nodeId");
        filter.getExcludes().add("nodeName");
        filter.getExcludes().add("parentNodeId");
        filter.getExcludes().add("allChildren");
        return filter;
    }
}
