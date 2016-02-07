package com.getjavajob.web06.roldukhine.pool;

import com.getjavajob.web06.roldukhine.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private static final int DEFAULT_SIZE = 10;

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";

    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> pool = new LinkedBlockingQueue<>(DEFAULT_SIZE);
    private ThreadLocal<ConnectionHolder> connectionHolder = new ThreadLocal<>();

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void setup(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }

        String url = properties.getProperty(PROPERTY_URL);
        String user = properties.getProperty(PROPERTY_USER);
        String password = properties.getProperty(PROPERTY_PASSWORD);

        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Connection connection = createConnection(url, user, password);
            if (connection != null) {
                pool.offer(connection);
            }
        }
    }

    private Connection createConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        if (connectionHolder.get() == null) {
            try {
                Connection connection = pool.poll(5, TimeUnit.SECONDS);
                ConnectionHolder holder = new ConnectionHolder(connection);
                connectionHolder.set(holder);
            } catch (InterruptedException e) {
                throw new DaoException("Cannot create connection.");
            }
        }

        ConnectionHolder holder = this.connectionHolder.get();
        holder.incrementAndGet();
        return holder.getConnection();
    }

    public void release() {
        ConnectionHolder holder = connectionHolder.get();
        if (holder == null) {
            return;
        }

        Connection connection = holder.getConnection();
        if (connection == null) {
            return;
        }

        holder.getAndDecrement();
        if (holder.getCounter() == 0 && pool.size() < DEFAULT_SIZE) {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                throw new DaoException("Error while releasing connection.");
            }
            this.connectionHolder.remove();
        }
    }

    public void close() {
        try {
            Connection connection = pool.take();
            connection.close();
        } catch (SQLException | InterruptedException e) {
            throw new DaoException(e);
        }
    }
}
