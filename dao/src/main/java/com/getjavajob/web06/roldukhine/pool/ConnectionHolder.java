package com.getjavajob.web06.roldukhine.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionHolder {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHolder.class);

    private Connection connection;

    private AtomicInteger counter = new AtomicInteger();

    public ConnectionHolder(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getAndDecrement() {
        return counter.getAndDecrement();
    }

    public int getAndIncrement() {
        return counter.getAndIncrement();
    }

    public int getCounter() {
        return counter.get();
    }

    public int incrementAndGet() {
        return counter.incrementAndGet();
    }
}
