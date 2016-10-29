package com.social_network.jdbc.dao;

import com.social_network.jdbc.model.User;

import java.util.Collection;

public interface UserDao {
    Collection<User> getAll();
    User getById();
    User getByFirstAndSecondName();
}