package com.example.chat.service;

import com.example.chat.dto.UserDto;
import com.example.chat.model.User;

import java.util.List;

public interface UserService {

    /**
     * Создать пользователя,
     * если пользователь создан то возвращает заполненный {@link UserDto UserDto},
     * если пользователь уже присутствует возвращает пустой {@link UserDto UserDto}
     *
     * @param user {@link User User}
     * @return {@link UserDto UserDto}
     */
    UserDto createUser(User user);

    /**
     * Получить список онлайн (активных) пользователей
     */
    List<UserDto> getUsersOnline();
}
