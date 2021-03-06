package com.social_network.jdbc.dao;

import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Collection<User> getAll();
    Optional<User> getById(long id);
    User getByFirstAndSecondName();

    Optional<User> isUserRegistered(String login, String password);

    void addNewUser(String firstName, String lastName, String email, String password, boolean male);
}