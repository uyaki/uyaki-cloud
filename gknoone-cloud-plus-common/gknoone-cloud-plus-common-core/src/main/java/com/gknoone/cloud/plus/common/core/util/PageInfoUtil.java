package com.gknoone.cloud.plus.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页插件工具
 *
 * @author noone
 * @date 2019-05-09 17:48
 */
public class PageInfoUtil {
    /**
     * 获取分页查询结果，适用于一般情况（先查询所有记录再分页）
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param list     所有结果
     * @return 分页结果
     */
    public static String getPageInfoJsonString(Integer pageNum, Integer pageSize, List<?> list) {
        return getPageInfoJsonString(pageSize,
                list.subList((pageNum - 1) * pageSize, pageNum * pageSize > list.size() ? list.size() : pageNum * pageSize),
                list.size());
    }

    /**
     * 获取分页查询结果，适用于大数据量情况（先查数据总量再查询当页数据）
     *
     * @param pageSize 每页条数
     * @param list     所有结果
     * @param count    总数
     * @return 分页查询结果
     */
    private static String getPageInfoJsonString(Integer pageSize, List<?> list, int count) {
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setTotal(count);
        pageInfo.setPages(count % pageSize == 0 ? count / pageSize : count / pageSize + 1);
        return JSON.toJSONString(pageInfo, JsonFilterUtil.getPageFilter(), SerializerFeature.WriteDateUseDateFormat);

    }
}
