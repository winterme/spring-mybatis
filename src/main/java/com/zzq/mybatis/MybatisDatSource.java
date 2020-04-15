package com.zzq.mybatis;

import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.logging.Logger;

/**
 * mybatis 数据连接实现
 */
public class MybatisDatSource implements DataSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MybatisDatSource.class);

    private static int MIN_POOL_SIZE;//最小连接数
    private static int MAX_POOL_SIZE;//最大连接数

    private static String USER;//用户名
    private static String PASSWORD;//密码
    private static String URL;//数据库连接地址
    private static String DRIVER_CLASS;//数据库驱动类名称

    private static String DEFAULT_PROPERTIES_FILE_NAME = "application.properties";  //默认得配置文件

    static {
        Properties properties = new Properties();
        for (String path : DEFAULT_PROPERTIES_FILE_NAME.split(",")) {
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {

                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 开始加载
        MIN_POOL_SIZE = Integer.valueOf(properties.getProperty("jdbc.pool.minsize", "5"));
        MAX_POOL_SIZE = Integer.valueOf(properties.getProperty("jdbc.pool.maxsize", "15"));
        USER = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");
        URL = properties.getProperty("jdbc.url");
        DRIVER_CLASS = properties.getProperty("jdbc.driver-class");

    }

    /**
     * 数据库连接池
     */
    private List<Connection> pool = Collections.synchronizedList(new LinkedList<Connection>());

    public MybatisDatSource() {
        initPool();
        log.debug("初始化DataSource：" + this);
    }

    private void initPool() {


        try {
            // 加载驱动
            Class.forName(DRIVER_CLASS);
            addConnection(MIN_POOL_SIZE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 向连接池添加连接对象
     *
     * @param size
     * @throws SQLException
     */
    private void addConnection(int size) throws SQLException {
        while (size > 0) {
            this.pool.add(DriverManager.getConnection(URL, USER, PASSWORD));
            --size;
        }
    }

    /**
     * 检查连接池容量，不足得时候进行扩容
     */
    private void checkCapacity() {
        if (this.pool.size() < 1) {
            // 连接池里面没有链接对象得时候进行扩容
            try {
                addConnection(5);
            } catch (SQLException e) {
                throw new RuntimeException("扩容失败！");
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        checkCapacity();

        // 取走第一个链接对象
        final Connection connection = this.pool.remove(0);

        /**
         * 使用 jdk动态代理，修改 connection 得 close方法
         *  当 连接池没有满得时候，就重新放入连接池，复用
         *      连接池满了就直接关闭。
         */
        Connection proxy = (Connection) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("close".equals(method.getName())) {
                    if (pool.size() < MAX_POOL_SIZE) {
                        pool.add(connection);   // 如果连接池没有满
                        log.debug("Connection 返回" + connection + "连接池");
                    } else {
                        connection.close();
                        log.debug("Connection 已关闭==>" + connection);
                    }
                    return null;
                }

                // 如果不是 close 就执行方法
                return method.invoke(connection, args);
            }
        });
        log.debug("获取到链接对象==>" + proxy);

        return proxy;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(URL, username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
