package com.social_network.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final boolean male;
}