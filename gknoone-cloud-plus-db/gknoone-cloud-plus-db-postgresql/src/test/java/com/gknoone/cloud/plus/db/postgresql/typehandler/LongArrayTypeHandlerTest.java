package com.gknoone.cloud.plus.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class LongArrayTypeHandlerTest {
    @Test
    public void getLongArray(){
        String columnValue = "{1,2,3}";
        Long[] l= {1L,2L,3L};
        assertArrayEquals(l,getLongArray(columnValue));
    }

    private Long[] getLongArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(columnValue);
        ArrayList<Long> list=new ArrayList<>();
        while (matcher.find()){
            list.add(Long.valueOf(matcher.group()));
        }

        return list.toArray(new Long[]{});
    }
}