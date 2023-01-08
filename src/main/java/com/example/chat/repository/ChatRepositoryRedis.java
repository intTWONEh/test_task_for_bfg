package com.example.chat.repository;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ChatRepositoryRedis implements ChatRepository {
    private RedisTemplate<String, String> redisTemplate;

    public List<ChatMessage> getChatMessages(String chatId, Long offset, Long size) {
        return redisTemplate.opsForZSet().range(chatId, offset, size)
                .stream()
                .map(JsonConverter::jsonToChatMessage)
                .collect(Collectors.toList());
    }

    @Override
    public void saveMessage(ChatMessage message) {
        redisTemplate.opsForZSet().add(
                message.getChatId(),
                JsonConverter.objectToJson(message),
                message.getTime()
        );
    }
}
