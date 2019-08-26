package com.gknoone.cloud.plus.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.util.SpringContextUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author noone
 */
@MappedTypes(UUID[].class)
@MappedJdbcTypes(JdbcType.OTHER)
public class UUIDArrayTypeHandler implements TypeHandler<UUID[]> {
    public static final Pattern pattern = Pattern.compile("[{}\"]");
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, UUID[] uuids, JdbcType jdbcType) throws SQLException {
        if (null == uuids) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("dataSource");
            Connection connection = dataSource.getConnection();
            Array array = connection.createArrayOf("uuid", uuids);
            connection.close();
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public UUID[] getResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getUUIDArray(columnValue);
    }

    @Override
    public UUID[] getResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getUUIDArray(columnValue);
    }

    @Override
    public UUID[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getUUIDArray(columnValue);
    }

    private UUID[] getUUIDArray(String columnValue) {
        if (null == columnValue) {
            return null;
        }
        Matcher m = pattern.matcher(columnValue);
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
