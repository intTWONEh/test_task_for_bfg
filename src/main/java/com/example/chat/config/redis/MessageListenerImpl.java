package com.example.chat.config.redis;

import com.example.chat.config.websocket.WebSocketSessionManager;
import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatRooms;
import com.example.chat.model.JsonConverter;
import com.example.chat.model.MessageType;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Обработчик сообщений из очереди Redis
 */
@Component
@AllArgsConstructor
public class MessageListenerImpl implements MessageListener {

    private WebSocketSessionManager webSocketSessionManager;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ChatMessage messageData = JsonConverter.jsonToChatMessage(message.toString());

        if (messageForEveryone(messageData)) {
            sendMessageAll(message);
        } else {
            sendMessageTo(messageData.getTo(), message);
        }
    }

    private void sendMessageTo(String userId, Message message) {
        try {
            WebSocketSession session = webSocketSessionManager.getWebSocketSessions(userId);

            if (session != null) {
                session.sendMessage(new TextMessage(message.getBody()));
            } else {
                //log not found session
            }
        } catch (IOException e) {
            //log except session
            throw new RuntimeException(e);
        }
    }

    private boolean messageForEveryone(ChatMessage messageData) {
        return ChatRooms.GENERAL.name().equalsIgnoreCase(messageData.getTo()) ||
                MessageType.USER_ONLINE.equals(messageData.getMessageType()) ||
                MessageType.USER_OFFLINE.equals(messageData.getMessageType());
    }

    private void sendMessageAll(Message message) {
        TextMessage textMessage = new TextMessage(message.getBody());

        webSocketSessionManager.getAllSessions().forEach(session -> {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                //log except session
                throw new RuntimeException(e);
            }
        });
    }
}
