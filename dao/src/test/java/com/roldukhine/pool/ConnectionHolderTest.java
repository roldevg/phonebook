package com.roldukhine.pool;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;

public class ConnectionHolderTest {

    @Test
    void getConnection() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assert.assertEquals(connection, connectionHolder.getConnection());
    }

    @Test
    void getAndDecrement() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assert.assertEquals(0, connectionHolder.getAndDecrement());
        Assert.assertEquals(-1, connectionHolder.getAndDecrement());
    }

    @Test
    void getAndIncrement() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assert.assertEquals(0, connectionHolder.getAndIncrement());
        Assert.assertEquals(1, connectionHolder.getAndIncrement());
    }

    @Test
    void incrementAndGet() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assert.assertEquals(1, connectionHolder.incrementAndGet());
        Assert.assertEquals(2, connectionHolder.incrementAndGet());
    }

    @Test
    void getCounter() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assert.assertEquals(1, connectionHolder.incrementAndGet());
        Assert.assertEquals(1, connectionHolder.getCounter());
    }
}
