package com.example.chat.repository;

import com.example.chat.dto.UserDto;
import com.example.chat.model.JsonConverter;
import com.example.chat.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserRepositoryRedis implements UserRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final String USER_ID = "USER_ID";

    private final String USER_ONLINE = "USER_ONLINE";

    @Override
    public void createUser(User user) {
        user.setId(
                redisTemplate.opsForValue().increment(USER_ID)
        );

        redisTemplate.opsForValue().set(
                user.getLogin(),
                JsonConverter.objectToJson(user)
        );
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String userData = redisTemplate.opsForValue().get(login);
        User user = null;

        if (userData != null) {
            user = JsonConverter.jsonToUser(userData);
        }

        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<UserDto> getUsersOnline() {
        return Objects.requireNonNull(redisTemplate.opsForSet().members(USER_ONLINE))
                .stream()
                .map(JsonConverter::jsonToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addInListUserOnline(User user) {
        redisTemplate.opsForSet().add(
                USER_ONLINE,
                getUserDtoInJson(user)
        );
    }

    @Override
    public void removeFromUserListOnline(User user) {
        redisTemplate.opsForSet().remove(
                USER_ONLINE,
                getUserDtoInJson(user)
        );
    }

    private String getUserDtoInJson(User user) {
        UserDto userDto = new UserDto();
        userDto.setData(user);
        return JsonConverter.objectToJson(userDto);
    }
}
