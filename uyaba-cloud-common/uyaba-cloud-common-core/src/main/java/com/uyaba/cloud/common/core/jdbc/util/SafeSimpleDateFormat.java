package com.uyaba.cloud.common.core.jdbc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author uyaba
 * @date 2019-10-12 19:17
 */
public class SafeSimpleDateFormat {
    private final String _format;

    private static final ThreadLocal _dateFormats = ThreadLocal.withInitial(() -> new HashMap());

    @SuppressWarnings("unchecked")
    private SimpleDateFormat getDateFormat(String format) {
        Map<String, SimpleDateFormat> formatters = (Map) _dateFormats.get();
        SimpleDateFormat formatter = formatters.get(format);
        if (formatter == null) {
            formatter = new SimpleDateFormat(format);
            formatters.put(format, formatter);
        }
        return formatter;
    }

    public SafeSimpleDateFormat(String format) {
        _format = format;
    }

    public String format(Date date) {
        return getDateFormat(_format).format(date);
    }

    public String format(Object date) {
        return getDateFormat(_format).format(date);
    }

    public Date parse(String day) throws ParseException {
        return getDateFormat(_format).parse(day);
    }
}
