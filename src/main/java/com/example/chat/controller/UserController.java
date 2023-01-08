package com.example.chat.controller;

import com.example.chat.dto.UserDto;
import com.example.chat.model.User;
import com.example.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("online")
    public List<UserDto> getUsersOnline() {
        return userService.getUsersOnline();
    }
}
