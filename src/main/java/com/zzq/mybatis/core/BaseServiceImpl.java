package com.zzq.mybatis.core;

import com.zzq.mybatis.MybatisDatSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 通用 mapper ，提供增删改查
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> {

    private static DataSource dataSource = new MybatisDatSource();
    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected Class<?> modelClass;

    private static final String SEPARA = "'";

    public BaseServiceImpl() {
        // 获取泛型
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<?>) type.getActualTypeArguments()[0];
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    public int save(T entity) {
        log.debug(">------------ORM INSERT Begin--------------<");
        log.debug("Method：Save  |  Object: " + entity);

        String sql = buildSQL(entity, SQLType.Insert);
        int i = execute(sql).intValue();
        log.debug(">------------ORM INSERT Down--------------<");
        return i;
    }

    /**
     * getById
     *
     * @param id
     * @return
     */
    public T getById(Object id) {
        try {
            log.debug(">------------ORM GETBYID Begin--------------<");
            log.debug("Method：getById  |  Object: " + id);

            T o = (T) modelClass.newInstance();
            Field field = getPrimaryKeyMethod(o);
            Method method = modelClass.getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());

            method.invoke(o, id);
            String sql = buildSQL(o, SQLType.QueryById);
            List<T> ts = executeQuery(sql);
            log.debug(">------------ORM GETBYID Down--------------<");

            if (ts.size() == 1) {
                return ts.get(0);
            } else if (ts.size() > 1) {
                throw new RuntimeException("通过id取出来的数据过多，这应该是bug!");
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取所有
     *
     * @return
     */
    public List<T> getAll() {
        log.debug(">------------ORM GETALL Begin--------------<");

        String sql = "";
        try {
            sql = buildSQL((T) modelClass.newInstance(), SQLType.QueryAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<T> ts = executeQuery(sql);
        log.debug(">------------ORM GETALL Down--------------<");

        return ts;
    }

    /**
     * 通过实体字段值进行查询
     *
     * @return
     */
    public List<T> getByEntity(T t) {
        log.debug(">------------ORM GETALL Begin--------------<");

        String sql = "";
        try {
            sql = buildSQL(t, SQLType.QueryByEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<T> ts = executeQuery(sql);
        log.debug(">------------ORM GETALL Down--------------<");

        return ts;
    }

    /**
     * sql 查询执行器
     *
     * @param sql
     * @param args
     * @return
     */
    private List<T> executeQuery(String sql, Object... args) {
        Connection conn = null;
        List<T> list = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = getStatement(sql, conn, args);
            ResultSet result = statement.executeQuery();
            log.debug("DB：SQL execute finnish!");
            list = parseResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    /**
     * 从 resultSet 里面获取对象，list
     *
     * @param result
     * @return
     */
    private List<T> parseResultSet(ResultSet result) {
        List<T> list = new ArrayList<>();
        try {
            Field[] fields = modelClass.getDeclaredFields();
            while (result.next()) {
                Map<String, String> entityColmun = entityColmun();
                T bean = (T) modelClass.newInstance();
                for (Field field : fields) {
                    String name = field.getName();
                    Object value = result.getObject(entityColmun.get(name));

                    Method method = modelClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), field.getType());
                    method.invoke(bean, value);
                }
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 建造sql语句
     *
     * @param entity
     * @param sqlType
     * @return
     */
    private String buildSQL(T entity, SQLType sqlType) {
        // 0. 构造sql
        StringBuffer sql = new StringBuffer();

        // 1. 获取表名称
        String tableName;
        if (entity.getClass().isAnnotationPresent(Table.class)) {
            Table table = entity.getClass().getAnnotation(Table.class);
            tableName = table.name();
        } else {
            tableName = entity.getClass().getSimpleName().toLowerCase();
        }

        // 2. 获取字段
        Map<String, Object> rowMap = entityChangeToRowMap(entity);

        // 表字段名
        Set<String> colums = rowMap.keySet();
        switch (sqlType) {
            case Insert: {
                sql.append("insert into " + tableName + "(");
                for (String colum : colums) {
                    if (!colum.equals(Id.class.getName())) {
                        if (rowMap.get(colum) != null) {
                            sql.append(colum + ",");
                        }
                    }
                }
                sql.deleteCharAt(sql.lastIndexOf(","));
                sql.append(") ");
                sql.append("values (");

                for (String colum : colums) {
                    if (!colum.equals(Id.class.getName())) {
                        Object o = rowMap.get(colum);
                        if (o instanceof String) {
                            o = SEPARA + o + SEPARA + ",";
                        } else if (o == null) {
                            continue;
                            //o = SEPARA + o + SEPARA + ",";
                        } else if (o instanceof Date) {
                            o = SEPARA + new SimpleDateFormat("yyyyMMddHHmmss").format(o) + SEPARA + ",";
                        }
                        sql.append(o);
                    }
                }
                sql.deleteCharAt(sql.lastIndexOf(","));
                sql.append(")");

                break;
            }
            case QueryById: {
                try {
                    sql.append("select ");
                    for (String colum : colums) {
                        if (!colum.equals(Id.class.getName())) {
                            sql.append(colum + ",");
                        }
                    }
                    sql.deleteCharAt(sql.lastIndexOf(","));

                    sql.append(" from ");
                    sql.append(tableName + " ");

                    Field field = getPrimaryKeyMethod(entity);
                    Method method = entity.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));

                    if (method == null) {
                        throw new RuntimeException("getById 没有发现 id");
                    }

                    if (rowMap.get(Id.class.getName()) != null) {
                        sql.append("where " + rowMap.get(Id.class.getName()) + "=" + SEPARA + method.invoke(entity) + SEPARA);
                    }

                    break;
                } catch (Exception e) {
                }
            }
            case QueryAll: {
                sql.append("select ");
                for (String colum : colums) {
                    if (!colum.equals(Id.class.getName())) {
                        sql.append(colum + ",");
                    }
                }
                sql.deleteCharAt(sql.lastIndexOf(","));

                sql.append(" from ");
                sql.append(tableName + " ");

                break;
            }
            case QueryByEntity: {
                sql.append("select ");
                for (String colum : colums) {
                    if (!colum.equals(Id.class.getName())) {
                        sql.append(colum + ",");
                    }
                }
                sql.deleteCharAt(sql.lastIndexOf(","));

                sql.append(" from ");
                sql.append(tableName + " ");

                sql.append("where 1=1 and ");

                for (String colmun : rowMap.keySet()) {
                    if (!Id.class.getName().equals(colmun)) { // 不等于
                        if (rowMap.get(colmun) != null) {
                            sql.append(colmun + "=" + SEPARA + rowMap.get(colmun) + SEPARA + " and");
                        }
                    }
                }

                sql.deleteCharAt(sql.lastIndexOf("a"));
                sql.deleteCharAt(sql.lastIndexOf("n"));
                sql.deleteCharAt(sql.lastIndexOf("d"));
            }
        }


        return sql.toString();
    }

    /**
     * 传入 实体对象，获取带有 @Id 注解的字段的 getXX 方法
     *
     * @param entity
     * @return
     * @throws NoSuchMethodException
     */
    private Field getPrimaryKeyMethod(T entity) throws NoSuchMethodException {
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }

        return null;
    }

    /**
     * 实体转表字段，并且赋值
     *
     * @param entity
     * @return
     */
    private Map<String, Object> entityChangeToRowMap(T entity) {
        Map<String, Object> rowMap = new LinkedHashMap<>();
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }

                Method method = entityClass.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                if (field.isAnnotationPresent(Column.class)) {
                    rowMap.put(field.getAnnotation(Column.class).name().toString(), method.invoke(entity));
                    // 该字段是 ID，特殊标记
                    if (field.isAnnotationPresent(Id.class)) {
                        rowMap.put(Id.class.getName(), field.getAnnotation(Column.class).name());
                    }
                } else {
                    rowMap.put(field.getName(), method.invoke(entity));
                    if (field.isAnnotationPresent(Id.class)) {
                        rowMap.put(Id.class.getName(), field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return rowMap;
    }


    /**
     * 获取实体字段名=> 数据库的字段名
     *
     * @return
     */
    private Map<String, String> entityColmun() {
        HashMap<String, String> rowMap = new LinkedHashMap<>();
        Field[] fields = modelClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }


            if (field.isAnnotationPresent(Column.class)) {
                rowMap.put(field.getName(), field.getAnnotation(Column.class).name());
                // 该字段是 ID，特殊标记
                if (field.isAnnotationPresent(Id.class)) {
                    rowMap.put(Id.class.getName(), field.getAnnotation(Column.class).name());
                }
            } else {
                rowMap.put(field.getName(), field.getName());
                if (field.isAnnotationPresent(Id.class)) {
                    rowMap.put(Id.class.getName(), field.getName());
                }
            }
        }
        return rowMap;
    }


    /**
     * 增删改的SQL执行器
     *
     * @param sql
     * @param args
     * @return 主键
     * @author 李恒名
     * @since 2016年3月16日
     */
    private Number execute(String sql, Object... args) {
        int result;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = getStatement(sql, conn, args);
            result = statement.executeUpdate();
            log.debug("SQL execute finnish!");

        } catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    private Number execute(String sql) {
        return execute(sql, new Object[]{});
    }

    private PreparedStatement getStatement(String sql, Connection conn,
                                           Object... args) throws SQLException {
        log.debug("SQL：" + sql);
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 1;
        for (Object object : args) {
            statement.setObject(i, object);
            ++i;
        }
        return statement;
    }

}
