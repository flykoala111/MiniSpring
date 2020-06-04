package com.fzj.minispring.springjdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;
import com.fzj.minispring.spring.GlobalParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 连接池（受保护）
 **/
class ConnectionPool {
    private final static String url = "minispring.jdbc.url";// 数据库连接池url
    private final static String driverClassName = "minispring.jdbc.driverClassName";// 数据库连接池驱动
    private final static String userName = "minispring.jdbc.userName"; // 数据库连接池用户名
    private final static String password = "minispring.jdbc.password";// 数据库连接池密码
    private final static String initialSize = "minispring.jdbc.initialSize";// 数据库连接池初始化连接数
    private final static String minIdle = "minispring.jdbc.minIdle";// 数据库连接池最小连接数
    private final static String maxActive = "minispring.jdbc.maxActive";// 数据库连接池最大连接数
    private final static String maxWait = "minispring.jdbc.maxWait";// 数据库连接池最大等待时间
    // 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接
    // 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
    private final static String timeBetweenEvictionRunsMillis = "minispring.jdbc.timeBetweenEvictionRunsMillis";
    private final static String removeAbandonedTimeout = "minispring.jdbc.removeAbandonedTimeout";// 数据库连接池对于建立时间超过removeAbandonedTimeout的连接强制关闭
    private final static String testWhileIdle = "minispring.jdbc.testWhileIdle";// 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效

    private ConnectionPool() {

    }

    /**
     * ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */
    private static DruidDataSource dataSource = null;

    // 禁止通过new方法调用类

    // 关闭方法
    static synchronized void close(Connection connection,
                                   PreparedStatement preparedStatement, ResultSet resultSet)
            throws MiniSpringException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "close connection error", e.getMessage(), e.getStackTrace());
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "close preparedStatement error", e.getMessage(), e.getStackTrace());
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "close resultSet error", e.getMessage(), e.getStackTrace());
            }
        }
    }

    // 连接方法
    static synchronized Connection getConnection() throws MiniSpringException {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MiniSpringException(0, "fzjspringjdbc", "required datasource failed", e.getMessage(), e.getStackTrace());
        }

        return connection;
    }

    /**
     * 获取连接池
     */
    static synchronized DataSource getDataSource() {
        //单例创建连接池
        if (dataSource == null) {
            dataSource = new DruidDataSource();
            dataSource.setUrl(GlobalParam.getProperties(url));
            dataSource.setDriverClassName(GlobalParam.getProperties(driverClassName));
            dataSource.setUsername(GlobalParam.getProperties(userName));
            dataSource.setPassword(GlobalParam.getProperties(password));
            dataSource.setInitialSize(Integer.valueOf(GlobalParam.getProperties(initialSize)));
            dataSource.setMinIdle(Integer.valueOf(GlobalParam.getProperties(minIdle)));
            dataSource.setMaxActive(Integer.valueOf(GlobalParam.getProperties(maxActive)));
            dataSource.setMaxWait(Integer.valueOf(GlobalParam.getProperties(maxWait)));
            dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(GlobalParam.getProperties(timeBetweenEvictionRunsMillis)));
            dataSource.setRemoveAbandonedTimeout(Integer.valueOf(GlobalParam.getProperties(removeAbandonedTimeout)));
            dataSource.setTestWhileIdle(Boolean.getBoolean(GlobalParam.getProperties(testWhileIdle)));
            new MiniSpringLog().printConsoleNontimeBlank(
                    "[Init minispringjdbc connection pool]",
                    "{",
                    "url:", GlobalParam.getProperties(url), ",",
                    "driverClassName:", GlobalParam.getProperties(driverClassName), ",",
                    "userName:", "******", ",",
                    "password:", "******", ",",
                    "initialSize:", GlobalParam.getProperties(initialSize), ",",
                    "minIdle:", GlobalParam.getProperties(minIdle), ",",
                    "maxActive:", GlobalParam.getProperties(maxActive), ",",
                    "maxWait:", GlobalParam.getProperties(maxWait), ",",
                    "timeBetweenEvictionRunsMillis:", GlobalParam.getProperties(timeBetweenEvictionRunsMillis), ",",
                    "removeAbandonedTimeout:", GlobalParam.getProperties(removeAbandonedTimeout), ",",
                    "testWhileIdle:", GlobalParam.getProperties(testWhileIdle),
                    "}"
            );
        }
        return dataSource;
    }
}
