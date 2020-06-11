package com.uyaba.cloud.db.postgresql.typehandler;

import com.uyaba.cloud.common.core.enums.MathSymbolEnum;
import com.uyaba.cloud.db.postgresql.contanst.PatternContansts;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * @author uyaba
 * @date 2019-09-09 08:56
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class UUIDArrayTypeHandler extends BaseTypeHandler<UUID[]> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UUID[] uuids, JdbcType jdbcType) throws SQLException {
        Connection connection = preparedStatement.getConnection();
        Array array = connection.createArrayOf("uuid", uuids);
        preparedStatement.setArray(i, array);
    }

    @Override
    public UUID[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getUUIDArray(columnValue);
    }

    @Override
    public UUID[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getUUIDArray(columnValue);
    }

    @Override
    public UUID[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getUUIDArray(columnValue);
    }

    private UUID[] getUUIDArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Matcher m = PatternContansts.ARRAY_CONVERSION_PATTERN.matcher(columnValue);
        columnValue = m.replaceAll("").trim();
        if (columnValue.isEmpty()) {
            return null;
        }
        String[] stringArray = columnValue.split(MathSymbolEnum.COMMA.symbol());
        UUID[] uuidArray = new UUID[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            uuidArray[i] = UUID.fromString(stringArray[i]);
        }
        return uuidArray;
    }
}
