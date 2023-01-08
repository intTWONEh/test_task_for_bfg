package com.example.chat.config.redis;

import com.example.chat.model.JsonConverter;
import com.example.chat.model.RedisChannel;
import com.example.chat.service.MessageSendingService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageSendingServiceImpl implements MessageSendingService {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendMessage(Object message) {
        redisTemplate.convertAndSend(
                RedisChannel.MESSAGES.name(),
                JsonConverter.objectToJson(message)
        );
    }
}
