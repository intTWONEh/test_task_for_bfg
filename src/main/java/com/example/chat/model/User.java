package com.example.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User {
    @JsonIgnore
    private Long id;

    private String login;
    private String password;
    private String name;

    @JsonIgnore
    private UserType userType = UserType.USER;

    public String getRoles() {
        return userType.name();
    }

    @JsonIgnore
    public String getUserId() {
        return id + ":" + name;
    }
}
