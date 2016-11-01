package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.SQLscriptExecuter;
import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class H2UserDaoTest {
    // TODO: 01.11.2016 дописать реализации
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        SQLscriptExecuter.initSqlData("src/test/resources/H2Init.sql");
    }

    @Test
    public void getAll() throws Exception {
        H2UserDao h2UserDao = new H2UserDao(connectionPool);
        Collection<User> users= h2UserDao.getAll();
        assertTrue(users.contains(new User(1, "Василий", "Бобков", "admin@exam.com", "123456", true)));
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
    }

    @Test
    public void addNewUser() throws Exception {
        H2UserDao h2UserDao = new H2UserDao(connectionPool);
        h2UserDao.addNewUser("Иван","Иванов","ivan@ya.ru","qwerty",true);
        Collection<User> users= h2UserDao.getAll();
        assertTrue(users.contains(new User(2, "Иван","Иванов","ivan@ya.ru","qwerty",true)));
    }
}