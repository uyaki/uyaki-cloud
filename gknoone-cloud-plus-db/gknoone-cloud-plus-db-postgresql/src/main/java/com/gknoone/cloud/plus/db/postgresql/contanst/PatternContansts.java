package com.gknoone.cloud.plus.db.postgresql.contanst;

import java.util.regex.Pattern;

/**
 * 正则常量
 *
 * @author gknoone
 * @date 2019-09-09 09:04
 */
public class PatternContansts {
    /**
     * 去除数组转换时候的特殊字符
     */
    public static final Pattern ARRAY_CONVERSION_PATTERN= Pattern.compile("[{}\"]");
}
