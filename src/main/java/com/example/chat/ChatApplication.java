package com.example.chat;

import com.example.chat.model.User;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Autowired
    void testUserAdd(UserService userService) {
       User user1 = new User();
       user1.setLogin("test");
       user1.setPassword("test");
       user1.setName("test");

       User user2 = new User();
       user2.setLogin("user");
       user2.setPassword("user");
       user2.setName("user");

       userService.createUser(user1);
       userService.createUser(user2);
    }
}
