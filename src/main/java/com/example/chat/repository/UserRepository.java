package com.example.chat.repository;

import com.example.chat.dto.UserDto;
import com.example.chat.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void createUser(User user);

    Optional<User> findByLogin(String login);

    List<UserDto> getUsersOnline();

    void addInListUserOnline(User user);

    void removeFromUserListOnline(User user);

}
