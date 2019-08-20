package com.gknoone.cloud.plus.common.db.postgresql.typehandler;

import com.alibaba.fastjson.JSON;
import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author noone
 */
@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class JsonTypeHandler implements TypeHandler<Object> {
    private static final String TRUE_STR = "true";
    private static final String FALSE_STR = "false";

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        if (null == o) {
            preparedStatement.setNull(i, Types.OTHER);
            return;
        } else if (o.getClass().equals(UUID.class)) {
            preparedStatement.setObject(i, o);
            return;
        } else if (o.getClass().equals(String.class)) {
            UUID uid = UUID.fromString(o.toString());
            preparedStatement.setObject(i, uid);
            return;
        }
        String result;
        try {
            result = JSON.toJSONString(o);
        } catch (Exception e) {
            if (o.getClass().equals(String.class) && !o.toString().startsWith(MathSymbolEnum.OPEN_BRACE.symbol()) && !o.toString().startsWith(MathSymbolEnum.OPEN_BRACKET.symbol())) {
                result = MathSymbolEnum.SINGLE_QUOTATION_MARKS.symbol() + o.toString() + MathSymbolEnum.SINGLE_QUOTATION_MARKS.symbol();
            } else if (o.getClass().equals(String.class) && o.toString().startsWith(MathSymbolEnum.OPEN_BRACE.symbol())) {
                result = o.toString();
            } else {
                result = null;
            }
        }
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(result);
        preparedStatement.setObject(i, pGobject);
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getObject(columnValue);
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getObject(columnValue);
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getObject(columnValue);
    }

    private Object getObject(String columnValue) {
        if (MathSymbolEnum.DOUBLE_QUOTATION_MARKS.symbol().equals(columnValue)) {
            return "";
        }
        if (TRUE_STR.equalsIgnoreCase(columnValue)) {
            return Boolean.TRUE;
        } else if (FALSE_STR.equalsIgnoreCase(columnValue)) {
            return Boolean.FALSE;
        }
        try {
            return UUID.fromString(columnValue);
        } catch (Exception e) {

        }
        try {
            return JSON.parseObject(columnValue, LinkedHashMap.class);
        } catch (Exception e) {

        }
        try {
            return JSON.parseObject(columnValue, ArrayList.class);
        } catch (Exception e) {

        }
        try {
            return Integer.parseInt(columnValue);
        } catch (Exception e) {

        }
        try {
            return Double.valueOf(columnValue);
        } catch (Exception e) {

        }
        return columnValue.replace(MathSymbolEnum.SINGLE_QUOTATION_MARKS.symbol(), "");
    }

}
