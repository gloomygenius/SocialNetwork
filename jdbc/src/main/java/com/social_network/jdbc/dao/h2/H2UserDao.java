package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.UserDao;
import com.social_network.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
public class H2UserDao implements UserDao {
    private Logger log = LoggerFactory.getLogger(H2UserDao.class);
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
                                resultSet.getString("name"),
                                resultSet.getString("name"),
                                resultSet.getString("name"),
                                resultSet.getString("name"),
                                resultSet.getBoolean("caliber")
                        ));
            // TODO: 29.10.2016 поля базы данных изменить
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
}