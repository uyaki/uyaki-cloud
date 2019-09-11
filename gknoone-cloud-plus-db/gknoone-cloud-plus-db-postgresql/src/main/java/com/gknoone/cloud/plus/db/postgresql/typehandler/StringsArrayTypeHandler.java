package com.gknoone.cloud.plus.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.db.postgresql.contanst.PatternContansts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gknoone
 * @date 2019-09-08 16:46
 */

@MappedJdbcTypes(JdbcType.ARRAY)
public class StringsArrayTypeHandler extends BaseTypeHandler<String[][]> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[][] strings, JdbcType jdbcType) throws SQLException {
        Connection connection = preparedStatement.getConnection();
        Array[] arrays = new Array[strings.length];
        for (int idx = 0; idx < strings.length; idx++) {
            Array array = connection.createArrayOf("varchar", strings[idx]);
            arrays[idx] = array;
        }
        Array array = connection.createArrayOf("varchar", arrays);
        preparedStatement.setArray(i, array);
    }

    @Override
    public String[][] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Array columnValue = resultSet.getArray(s);
        return getStringsArray(columnValue);
    }

    @Override
    public String[][] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Array columnValue = resultSet.getArray(i);
        return getStringsArray(columnValue);
    }

    @Override
    public String[][] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Array columnValue = callableStatement.getArray(i);
        return getStringsArray(columnValue);
    }

    private String[][] getStringsArray(Array columnValue) throws SQLException {
        if (null == columnValue) {
            return null;
        }
        return (String[][]) columnValue.getArray();
    }
}
