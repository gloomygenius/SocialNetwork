package com.social_network.jdbc.dao.h2;

import com.social_network.jdbc.connection_pool.ConnectionPool;
import com.social_network.jdbc.connection_pool.ConnectionPoolException;
import com.social_network.jdbc.dao.UserInfoDao;
import com.social_network.jdbc.model.UserInfo;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
public class H2UserInfo implements UserInfoDao {
    private final ConnectionPool connectionPool;

    @Override
    public Optional<UserInfo> getById(int userId) {
        Optional<UserInfo> userInfo = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT birthday, city, university, team, position FROM Info WHERE user_id='" + userId + "'"
             )) {
            if (resultSet.next()) {
                System.out.println("Info was found!");
                userInfo.of(new UserInfo(
                        userId,
                        //resultSet.getDate("birthday").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        LocalDate.now(),
                        resultSet.getString("city"),
                        resultSet.getString("university"),
                        resultSet.getString("team"),
                        resultSet.getString("position")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
            // TODO: 02.11.2016 написать логи
        }
        return userInfo;
    }
}