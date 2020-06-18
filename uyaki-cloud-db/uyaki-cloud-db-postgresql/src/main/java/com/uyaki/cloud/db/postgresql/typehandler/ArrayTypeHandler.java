package com.uyaki.cloud.db.postgresql.typehandler;

import com.uyaki.cloud.common.core.enums.MathSymbolEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.*;

/**
 * @author uyaki
 * @date 2019-09-08 16:02
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class ArrayTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        Connection connection = preparedStatement.getConnection();
        List<Object> resultList = new ArrayList<>();
        String typeName = null;
        for (Object item : (List) o) {
            if (item.getClass().equals(String.class)) {
                typeName = "varchar";
                resultList.add(item);
            } else if (item.getClass().equals(Long.class)) {
                typeName = "bigint";
                resultList.add(item);
            } else if (item.getClass().equals(Integer.class)) {
                typeName = "int";
                resultList.add(item);
            } else if (item.getClass().equals(UUID.class)) {
                typeName = "uuid";
                resultList.add(item);
            } else if (item.getClass().equals(LinkedHashMap.class)) {
                typeName = "point";
                StringBuffer sb = new StringBuffer(((Map<String, String>) item).get("point"));
                sb.insert(0, MathSymbolEnum.OPEN_PAREN.symbol());
                sb.append(MathSymbolEnum.CLOSE_PAREN.symbol());
                resultList.add(sb.toString());
            } else {
                typeName = "varchar";
                resultList.add(item.toString());
            }
        }
        if (typeName == null) {
            preparedStatement.setNull(i, Types.ARRAY);
        } else {
            Array array = connection.createArrayOf(typeName, resultList.toArray());
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Array array = resultSet.getArray(s);
        return array == null ? null : array.getArray();
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Array array = resultSet.getArray(i);
        return array == null ? null : array.getArray();
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Array array = callableStatement.getArray(i);
        return array == null ? null : array.getArray();
    }
}
