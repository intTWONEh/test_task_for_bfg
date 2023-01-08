package com.example.chat.model;

import com.example.chat.dto.UserDto;
import com.google.gson.Gson;

public abstract class JsonConverter {
    private static final Gson converter = new Gson();

    public static ChatMessage jsonToChatMessage(String json) {
        return converter.fromJson(json, ChatMessage.class);
    }

    public static User jsonToUser(String json) {
        return converter.fromJson(json, User.class);
    }

    public static UserDto jsonToUserDto(String json) {
        return converter.fromJson(json, UserDto.class);
    }

    public static String objectToJson(Object o) {
        return converter.toJson(o);
    }
}
