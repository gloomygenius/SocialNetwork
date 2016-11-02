package com.social_network.jdbc.dao;

import com.social_network.jdbc.model.UserInfo;

import java.util.Optional;

public interface UserInfoDao {
    Optional<UserInfo> getById(int id);
}