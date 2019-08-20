package com.gknoone.cloud.plus.common.db.postgresql.typehandler;

import com.gknoone.cloud.plus.common.core.enums.MathSymbolEnum;
import com.gknoone.cloud.plus.common.core.util.SpringContextUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * 数组的mybatis类型处理器
 *
 * @author noone
 */
@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class ArrayTypeHandler implements TypeHandler<Object> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        if (null == o || !o.getClass().equals(ArrayList.class)) {
            preparedStatement.setNull(i, Types.ARRAY);
        } else {
            DataSource dataSource = (DataSource) SpringContextUtil.getBean("dataSource");
            Connection connection = dataSource.getConnection();
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
            if (typeName != null) {
                preparedStatement.setNull(i, Types.ARRAY);
            } else {
                Array array = connection.createArrayOf(typeName, resultList.toArray());
                connection.close();
                preparedStatement.setArray(i, array);
            }
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        Array array = resultSet.getArray(s);
        return array == null ? null : array.getArray();
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        Array array = resultSet.getArray(i);
        return array == null ? null : array.getArray();
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        Array array = callableStatement.getArray(i);
        return array == null ? null : array.getArray();
    }
}
