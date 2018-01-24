package com.roldukhine.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;

public class ConnectionHolderTest {

    @Test
    void getConnection() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assertions.assertEquals(connection, connectionHolder.getConnection());
    }

    @Test
    void getAndDecrement() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assertions.assertEquals(0, connectionHolder.getAndDecrement());
        Assertions.assertEquals(-1, connectionHolder.getAndDecrement());
    }

    @Test
    void getAndIncrement() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assertions.assertEquals(0, connectionHolder.getAndIncrement());
        Assertions.assertEquals(1, connectionHolder.getAndIncrement());
    }

    @Test
    void incrementAndGet() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assertions.assertEquals(1, connectionHolder.incrementAndGet());
        Assertions.assertEquals(2, connectionHolder.incrementAndGet());
    }

    @Test
    void getCounter() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionHolder connectionHolder = new ConnectionHolder(connection);
        Assertions.assertEquals(1, connectionHolder.incrementAndGet());
        Assertions.assertEquals(1, connectionHolder.getCounter());
    }
}
