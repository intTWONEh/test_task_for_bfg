package com.example.chat.dto;

import com.example.chat.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;

    public void setData(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public String getUserId() {
        return id + ":" + name;
    }
}
