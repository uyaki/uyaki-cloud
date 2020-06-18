package com.uyaki.cloud.db.postgresql.typehandler;

import com.uyaki.cloud.common.core.enums.MathSymbolEnum;
import com.uyaki.cloud.db.postgresql.contanst.PatternContansts;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.regex.Matcher;

/**
 * @author uyaki
 * @date 2019-09-09 08:49
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        Connection connection = preparedStatement.getConnection();
        Array array = connection.createArrayOf("varchar", strings);
        preparedStatement.setArray(i, array);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getStringArray(columnValue);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getStringArray(columnValue);
    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getStringArray(columnValue);
    }

    private String[] getStringArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Matcher m = PatternContansts.ARRAY_CONVERSION_PATTERN.matcher(columnValue);
        columnValue = m.replaceAll("").trim();
        if (columnValue.isEmpty()) {
            return null;
        }
        return columnValue.split(MathSymbolEnum.COMMA.symbol());
    }
}
