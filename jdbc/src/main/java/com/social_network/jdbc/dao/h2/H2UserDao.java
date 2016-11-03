package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, first_name, last_name, email, password, male, birthday, city, university," +
                             " team, position FROM Users"
             )) {
            while (resultSet.next())
                users.add(createUserFromResultSet(resultSet));
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return users;
    }

    @Override
    public Optional<User> getById(long id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, first_name, last_name, email, password, male, birthday, city, university, " +
                             "team, position FROM Users WHERE id='" + id + "'"
             )) {
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return user;
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
                     "SELECT id, first_name, last_name, email, password, male, birthday, city, university, team, " +
                             "position FROM Users WHERE email='" + login + "'AND password='" + password + "'"
             )) {
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return user;
    }

    @Override
    public void addNewUser(String firstName, String lastName, String email, String password, boolean male) {
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Users (first_name, last_name, email, password, male) " +
                    "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + password + "', " + male + ");");
        } catch (SQLException | ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("male"),
                    convertDateToLocalDate(resultSet.getDate("birthday")),
                    //LocalDate.now(), // TODO: 03.11.2016  написать нормальную функцию даты
                    resultSet.getString("city"),
                    resultSet.getString("university"),
                    resultSet.getString("team"),
                    resultSet.getString("position")
            );
        } catch (SQLException e) {
            log.error("Error of creating user from result set", e);
        }
        return user;
    }

    LocalDate convertDateToLocalDate(Date date) {
        if (date==null) return null;
        LocalDate localDate =
                Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
        return localDate;
    }
}