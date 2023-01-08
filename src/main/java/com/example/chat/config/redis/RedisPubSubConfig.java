package com.example.chat.config.redis;

import com.example.chat.model.RedisChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Objects;

/**
 * Конфигурация очереди Redis
 */
@Configuration
public class RedisPubSubConfig {

    /**
     * Инициализация канала для сообщений
     */
    @Bean
    public RedisMessageListenerContainer init(RedisTemplate<String, String> redisTemplate, MessageListener messageListener) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisMessageListenerContainer.addMessageListener(
                messageListener,
                ChannelTopic.of(RedisChannel.MESSAGES.name())
        );

        return redisMessageListenerContainer;
    }

}
