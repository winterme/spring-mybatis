package com.zzq.mybatis;

import com.zzq.mybatis.annotation.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PkuInvocationHandle implements InvocationHandler {

    private Logger LOG = LoggerFactory.getLogger(PkuInvocationHandle.class);

    private static final MybatisDatSource mybatisDatSource = new MybatisDatSource();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("save".equals(method.getName())){
            int i =10;
        }

        if (method.isAnnotationPresent(Select.class)) {
            String sql = method.getAnnotation(Select.class).value();

            Pattern compile = Pattern.compile("\\{.*?}");
            Matcher matcher = compile.matcher(sql);

            if (matcher.find()) {
                StringBuffer newSql = new StringBuffer();
                String[] split = sql.split("\\{.*?}");
                for (int i = 0; i < split.length; i++) {
                    newSql.append(split[i]).append("\"" + args[i] + "\"");
                }
                sql = newSql.toString();
            }

            //Object list = jdbcHelper.list(sql);
            Connection connection = mybatisDatSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            LinkedList<String> results = new LinkedList<String>();
            try {

                LinkedList<String> colNames = getColumnNames(resultSet);
                StringBuilder stringBuilder = new StringBuilder();
                while (resultSet.next()) {
                    stringBuilder.setLength(0);
                    for (int i = 1; i <= colNames.size(); i++) {
                        String columnValue = resultSet.getString(i);
                        stringBuilder.append(columnValue + "\t");
                    }
                    results.add(stringBuilder.toString().trim());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(resultSet, preparedStatement, connection);
            }

            LOG.debug("sql ===> " + sql);

            return results;
        }

        if (method.getName().equals("toString")) {
            return proxy.getClass().getInterfaces()[0].getName();
        }

        return null;
    }

    /**
     * 获取表列名
     *
     * @param rs_
     * @return
     * @throws SQLException
     */
    private LinkedList<String> getColumnNames(ResultSet rs_) throws SQLException {
        LinkedList<String> colNames = null;
        ResultSetMetaData metaData = rs_.getMetaData();
        int cols = metaData.getColumnCount();
        if (cols > 0) {
            colNames = new LinkedList<String>();
            for (int i = 1; i <= cols; i++) {
                colNames.add(metaData.getColumnName(i));
            }
        }
        return colNames;
    }

    /**
     * 释放数据库连接资源
     */
    public void close(AutoCloseable... closeables) {
        try {
            for (AutoCloseable closeable : closeables) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
