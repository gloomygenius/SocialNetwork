package com.social_network.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final boolean male;
    private final LocalDate birthday;
    private final String city;
    private final String university;
    private final String team;
    private final String position;
}