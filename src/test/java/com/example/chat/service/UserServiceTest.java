package com.example.chat.service;

import com.example.chat.dto.UserDto;
import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setName("test");
    }

    @Test
    void createUserNew() {
        Mockito.when(userRepository.findByLogin("test"))
                .thenReturn(Optional.empty());

        UserDto userDto = userService.createUser(user);

        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    void createUserExisting() {
        Mockito.when(userRepository.findByLogin("test"))
                .thenReturn(Optional.of(user));

        UserDto userDto = userService.createUser(user);

        assertNotEquals(userDto.getName(), user.getName());
        assertNull(userDto.getName());
    }

    @Test
    void getUsersOnlineAllOffline() {
        Mockito.when(userRepository.getUsersOnline())
                .thenReturn(List.of());

        List<UserDto> userOnline = userService.getUsersOnline();

        assertTrue(userOnline.isEmpty());
    }

    @Test
    void getUsersOnlineOneUser() {
        Mockito.when(userRepository.getUsersOnline())
                .thenReturn(List.of(new UserDto()));

        List<UserDto> userOnline = userService.getUsersOnline();

        assertEquals(userOnline.size(), 1);
    }
}