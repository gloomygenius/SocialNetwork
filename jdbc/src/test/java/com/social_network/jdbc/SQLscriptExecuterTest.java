package com.social_network.jdbc;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.h2.H2UserDao;
import com.social_network.jdbc.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

public class SQLscriptExecuterTest {

    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @Test
    public void initSqlData() throws Exception {
        SQLscriptExecuter.initSqlData("src/test/resources/H2Init.sql");
        H2UserDao h2UserDao = new H2UserDao(connectionPool);
        Collection<User> users = h2UserDao.getAll();
        assertTrue(users.contains(new User(1, "Василий", "Бобков", "admin@exam.com", "123456", true)));
    }
}