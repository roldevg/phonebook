package com.getjavajob.roldukhine.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionHolder {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHolder.class);

    private Connection connection;

    private AtomicInteger counter = new AtomicInteger();

    public ConnectionHolder(Connection connection) {
        logger.debug("create ConnectionHolder {}", connection);
        this.connection = connection;
    }

    public Connection getConnection() {
        logger.debug("return getConnection {}", connection);
        return connection;
    }

    public int getAndDecrement() {
        int decrementValue = counter.getAndDecrement();
        logger.debug("decrementValue {}", decrementValue);
        return decrementValue;
    }

    public int getAndIncrement() {
        int incrementValue = counter.getAndIncrement();
        logger.debug("incrementValue {}", incrementValue);
        return incrementValue;
    }

    public int getCounter() {
        int counter = this.counter.get();
        logger.debug("counter {}", counter);
        return counter;
    }

    public int incrementAndGet() {
        int increment = counter.incrementAndGet();
        logger.debug("increment {}", increment);
        return increment;
    }
}