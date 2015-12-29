package com.getjavajob.web06.roldukhine.pool;

import com.getjavajob.web06.roldukhine.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private static final int poolSize = 10;

    private static ConnectionPool instance = new ConnectionPool();

    private BlockingQueue<Connection> pool = new LinkedBlockingQueue<>(poolSize);

    private ThreadLocal<ConnectionHolder> connectionHolder = new ThreadLocal<>();

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void setup(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }

        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        for (int i = 0; i < poolSize; i++) {
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
            connection.setAutoCommit(false);
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
                throw new DaoException("Cannot create connection");
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

        if (holder.getCounter() == 0 && pool.size() < poolSize) {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.connectionHolder.remove();
        }
    }

    public void close() {
        while (!pool.isEmpty()) {
            try {
                Connection connection = pool.take();
                connection.close();
            } catch (SQLException | InterruptedException e) {
                throw new DaoException(e);
            }
        }
    }
}
