package com.gknoone.cloud.plus.common.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.util.SpringContextUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
@MappedTypes(String[][].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class StringsArrayTypeHandler implements TypeHandler<String[][]> {
    public static final Pattern pattern = Pattern.compile("[{}\"]");
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String[][] strings, JdbcType jdbcType) throws SQLException {
        if (null == strings) {
            preparedStatement.setNull(i, Types.ARRAY);
        } else {
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("dataSource");
            Connection connection = dataSource.getConnection();
            Array[] arrays = new Array[strings.length];
            for (int idx = 0; idx < strings.length; idx++) {
                Array array = connection.createArrayOf("varchar", strings[idx]);
                arrays[idx] = array;
            }
            Array array = connection.createArrayOf("varchar", arrays);
            connection.close();
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public String[][] getResult(ResultSet resultSet, String s) throws SQLException {
        Array columnValue = resultSet.getArray(s);
        return getStringsArray(columnValue);
    }

    @Override
    public String[][] getResult(ResultSet resultSet, int i) throws SQLException {
        Array columnValue = resultSet.getArray(i);
        return getStringsArray(columnValue);
    }

    @Override
    public String[][] getResult(CallableStatement callableStatement, int i) throws SQLException {
        Array columnValue = callableStatement.getArray(i);
        return getStringsArray(columnValue);
    }

    private String[][] getStringsArray(Array columnValue) throws SQLException {
        if (null == columnValue) {
            return null;
        }
        String[] arrays = (String[]) columnValue.getArray();
        if (ArrayUtils.isEmpty(arrays)) {
            return null;
        }
        String[][] strings = new String[arrays.length][];
        for (int i = 0; i < arrays.length; i++) {
            String str = arrays[i];
            Matcher m = pattern.matcher(str);
            str = m.replaceAll("").trim();
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            String[] strArr = str.split(MathSymbolEnum.COMMA.symbol());
            strings[i] = strArr;
        }
        return strings;
    }
}
