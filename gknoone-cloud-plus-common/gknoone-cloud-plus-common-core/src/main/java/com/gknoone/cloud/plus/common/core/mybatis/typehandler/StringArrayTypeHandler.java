package com.gknoone.cloud.plus.common.core.mybatis.typehandler;


import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.util.SpringContextUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import javax.sql.DataSource;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author noone
 */
@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class StringArrayTypeHandler implements TypeHandler<String[]> {
    public static final Pattern pattern = Pattern.compile("[{}\"]");

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        if (null == strings) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            StringBuffer result = new StringBuffer();
            for (String item : strings) {
                result.append(item).append(MathSymbolEnum.COMMA.symbol());
            }
            if (0 != result.length()) {
                result.deleteCharAt(result.length() - 1);
            }
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("dataSource");
            Connection connection = dataSource.getConnection();
            Array array = connection.createArrayOf("varchar", strings);
            connection.close();
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public String[] getResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getStringArray(columnValue);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getStringArray(columnValue);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getStringArray(columnValue);
    }

    private String[] getStringArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Matcher m = pattern.matcher(columnValue);
        columnValue = m.replaceAll("").trim();
        if (columnValue.isEmpty()) {
            return null;
        }
        String[] stringArray = columnValue.split(MathSymbolEnum.COMMA.symbol());
        return stringArray;
    }
}
