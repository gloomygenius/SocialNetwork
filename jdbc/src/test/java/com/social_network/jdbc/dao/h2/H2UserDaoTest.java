package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Vasiliy on 30.10.2016.
 */
public class H2UserDaoTest {
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        connectionPool.initSqlData("src/test/resources/H2Init.sql");
    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void getByFirstAndSecondName() throws Exception {

    }

    @Test
    public void isUserRegistered() throws Exception {
        H2UserDao h2UserDao = new H2UserDao(connectionPool);
        Optional<User> user = h2UserDao.isUserRegistered("admin@exam.com", String.valueOf(123456));
        assertTrue(user.isPresent());
        assertThat(user.get().getFirstName(),is("Василий"));
        System.out.println(user.get().getFirstName());
    }
}