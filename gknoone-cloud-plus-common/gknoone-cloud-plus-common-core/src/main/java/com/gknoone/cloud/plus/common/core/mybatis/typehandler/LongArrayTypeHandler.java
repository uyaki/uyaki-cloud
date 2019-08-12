package com.gknoone.cloud.plus.common.core.mybatis.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.util.SpringContextUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author noone
 */
@MappedTypes(Long[].class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class LongArrayTypeHandler implements TypeHandler<Long[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Long[] longs, JdbcType jdbcType) throws SQLException {
        if (null == longs) {
            preparedStatement.setNull(i, Types.ARRAY);
        } else {
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("dataSource");
            Connection connection = dataSource.getConnection();
            Array array = connection.createArrayOf("bigint", longs);
            connection.close();
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public Long[] getResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getLongArray(columnValue);
    }

    @Override
    public Long[] getResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getLongArray(columnValue);
    }

    @Override
    public Long[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getLongArray(columnValue);
    }

    private Long[] getLongArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        String[] stringArray = columnValue.split(MathSymbolEnum.COMMA.symbol());
        Long[] longArray = new Long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longArray[i] = Long.valueOf(stringArray[i]);
        }
        return longArray;
    }
}
