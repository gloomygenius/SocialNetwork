package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
public class H2UserDao implements UserDao {
    private Logger log = LogManager.getLogger(H2UserDao.class);
    private final ConnectionPool connectionPool;

    @Override
    public Collection<User> getAll() {
        Collection<User> users = new HashSet<>();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, caliber FROM Gun")) {
            while (resultSet.next())
                users.add(
                        new User(
                                resultSet.getLong("id"),
                                resultSet.getString("firs_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getBoolean("male")
                        ));
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return users;
    }

    @Override
    public User getById() {
        // TODO: 29.10.2016 написать реализацию
        return null;
    }

    @Override
    public User getByFirstAndSecondName() {
        // TODO: 29.10.2016 написать реализацию
        return null;
    }

    @Override
    public Optional<User> isUserRegistered(String login, String password) {
        Optional<User> user = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, first_name, last_name, email, password, male  " +
                             "FROM Users WHERE email='" + login + "'AND password='" + password + "'"
             )) {
            if (resultSet.next()) {
                user = Optional.of(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("male")
                ));
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return user;
    }
}