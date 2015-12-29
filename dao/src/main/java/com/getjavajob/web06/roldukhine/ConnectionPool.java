package com.getjavajob.web06.roldukhine;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private static final String CONNECTION_PROPERTIES = "connection.properties";

    private static Connection connection;

    private static ConnectionPool ourInstance = new ConnectionPool();

    private ConnectionPool() {
        try {
            Properties properties = new Properties();
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES));
            String uri = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            ConnectionFactory connectionFactory = getConnectionFactory(uri, user, password);
            PoolableConnectionFactory poolFactory = new PoolableConnectionFactory(connectionFactory, null);
            ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolFactory);
            poolFactory.setPool(connectionPool);
            PoolingDriver dbcpDriver = getDBCPDriver();
            dbcpDriver.registerPool("phonebook", connectionPool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    public static void releaseConnection(Connection con) {
        Utils.closeQuietly(con);
    }

    private ConnectionFactory getConnectionFactory(String uri, String user, String password) {
        return new DriverManagerConnectionFactory(
                uri, user, password);
    }

    private PoolingDriver getDBCPDriver() {
        PoolingDriver driver = null;
        try {
            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
            driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:phonebook");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
