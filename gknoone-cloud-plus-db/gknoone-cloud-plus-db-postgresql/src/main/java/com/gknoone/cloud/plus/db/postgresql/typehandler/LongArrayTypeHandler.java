package com.gknoone.cloud.plus.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.db.postgresql.contanst.PatternContansts;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gknoone
 * @date 2019-09-08 16:20
 */
@MappedJdbcTypes(JdbcType.ARRAY)
public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Long[] longs, JdbcType jdbcType) throws SQLException {
        Connection connection = preparedStatement.getConnection();
        Array array = connection.createArrayOf("bigint", longs);
        preparedStatement.setArray(i, array);
    }

    @Override
    public Long[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getLongArray(columnValue);
    }

    @Override
    public Long[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getLongArray(columnValue);
    }

    @Override
    public Long[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getLongArray(columnValue);
    }

    private Long[] getLongArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Matcher matcher = PatternContansts.STRING_WITH_OPEN_BRACE_TO_DIGITAL_PATTERN.matcher(columnValue);
        ArrayList<Long> list=new ArrayList<>();
        while (matcher.find()){
            list.add(Long.valueOf(matcher.group()));
        }
        return list.toArray(new Long[]{});
    }
}
