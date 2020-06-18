package com.uyaki.cloud.db.postgresql.contanst;

import java.util.regex.Pattern;

/**
 * 正则常量
 *
 * @author uyaki
 * @date 2019-09-09 09:04
 */
public class PatternContansts {
    /**
     * 去除数组转换时候的特殊字符
     */
    public static final Pattern ARRAY_CONVERSION_PATTERN= Pattern.compile("[{}\"]");
    /**
     * 带花括号字符串提取数字
     */
    public static final Pattern STRING_WITH_OPEN_BRACE_TO_DIGITAL_PATTERN = Pattern.compile("-?\\d+");
}
