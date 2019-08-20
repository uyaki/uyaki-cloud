package com.gknoone.cloud.plus.common.db.postgresql.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.UUID;

/**
 * @author noone
 */
@MappedTypes(UUID.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class UUIDTypeHandler implements TypeHandler<UUID> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType) throws SQLException {
        if (null == uuid) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            preparedStatement.setObject(i, uuid);
        }
    }

    @Override
    public UUID getResult(ResultSet resultSet, String s) throws SQLException {
        String columnValue = resultSet.getString(s);
        return getUUID(columnValue);
    }

    @Override
    public UUID getResult(ResultSet resultSet, int i) throws SQLException {
        String columnValue = resultSet.getString(i);
        return getUUID(columnValue);
    }

    @Override
    public UUID getResult(CallableStatement callableStatement, int i) throws SQLException {
        String columnValue = callableStatement.getString(i);
        return getUUID(columnValue);
    }

    private UUID getUUID(String columnValue) {
        return null == columnValue ? null : UUID.fromString(columnValue);
    }

}
