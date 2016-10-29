package com.social_network.jdbc.connection_pool;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConnectionPoolTest {
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void createConnectionPoolTest() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @Test
    public void connectionInitTest() {
        assertThat(connectionPool.driverName, is("org.h2.Driver"));
        assertThat(connectionPool.user, is(""));
        assertThat(connectionPool.password, is(""));
        assertThat(connectionPool.url, is("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"));
        assertThat(connectionPool.poolSize, is(5));
    }

    @Test
    public void initPoolData() throws Exception {
        assertFalse(connectionPool.getConnectionQueue().isEmpty());
    }

    @Test
    public void dispose() throws Exception {
        // TODO: 28.10.2016 надо придумать тест
    }


    @Test
    public void takeConnection() throws Exception {
        Connection connection = connectionPool.takeConnection();
        BlockingQueue<Connection> givenConnectionQueue = connectionPool.getGivenAwayConQueue();
        assertFalse(givenConnectionQueue.isEmpty());
        assertNotNull(connection);
    }

    @Test
    public void closeConnection() throws Exception {
        Connection connection = connectionPool.takeConnection();
        BlockingQueue<Connection> givenConnectionQueue = connectionPool.getGivenAwayConQueue();
        assertTrue(givenConnectionQueue.contains(connection));
        connection.close();
        BlockingQueue<Connection> connectionQueue = connectionPool.getConnectionQueue();
        assertTrue(connectionQueue.contains(connection));
    }

}