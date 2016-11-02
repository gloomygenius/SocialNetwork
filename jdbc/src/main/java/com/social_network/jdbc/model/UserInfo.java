package com.social_network.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class UserInfo {
    private final int user_id;
    private final LocalDate birthday;
    private final String city;
    private final String university;
    private final String team;
    private final String position;
}
