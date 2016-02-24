package com.getjavajob.web06.roldukhine.pool;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionPoolTest {

    private Properties getConnectionProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:h2:~/phonebook");
        properties.setProperty("user", "root");
        properties.setProperty("password", "");
        return properties;
    }

    @Test
    public void testSingleton() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ConnectionPool connectionPool2 = ConnectionPool.getInstance();
        Assert.assertEquals(connectionPool, connectionPool2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetupWithNullProperties() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.setup(null);
    }

    @Test
    public void testSetup() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);
    }

    @Test
    public void testGetConnection() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);
        connectionPool.getConnection();
    }

    @Test
    public void testClose() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);
        connectionPool.close();
    }

    @Test
    public void testRelease() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);
        connectionPool.release();
        connectionPool.close();
    }

    @Test
    public void testGetConnectionSingleThread() {
        final ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = connectionPool.getConnection();
                Connection connection2 = connectionPool.getConnection();
                Assert.assertEquals(connection, connection2);
            }
        });
        thread.start();
    }

    @Test
    public void testGetConnectionMultipleThread() {
        final ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);

        final Connection connection = connectionPool.getConnection();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection2 = connectionPool.getConnection();
                Assert.assertNotEquals(connection, connection2);
            }
        });
        thread.start();
    }

}