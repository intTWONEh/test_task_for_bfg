package com.example.chat.service;

import com.example.chat.dto.UserDto;
import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(User user) {
        UserDto userDto = new UserDto();

        if (userRepository.findByLogin(user.getLogin()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.createUser(user);
            userDto.setData(user);
        }

        return userDto;
    }

    @Override
    public List<UserDto> getUsersOnline() {
        return userRepository.getUsersOnline();
    }

}
