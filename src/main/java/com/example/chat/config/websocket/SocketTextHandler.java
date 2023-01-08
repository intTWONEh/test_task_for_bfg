package com.example.chat.config.websocket;

import com.example.chat.config.security.SecurityUser;
import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatRooms;
import com.example.chat.model.JsonConverter;
import com.example.chat.model.User;
import com.example.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

/**
 * Конфигурация действий WebSocket
 */
@Component
@AllArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {

    private ChatService chatService;

    private WebSocketSessionManager webSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        User user = getUser(session);

        if (user != null) {
            webSocketSessionManager.addWebSocketSession(user.getUserId(), session);
            chatService.userHasEnteredChat(user);
        } else {
            //log user offline
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        User user = getUser(session);

        if (user != null) {
            webSocketSessionManager.removeWebSocketSession(user.getUserId());
            chatService.userHasLeftChat(user);
        } else {
            //log user offline
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        ChatMessage chatMessage = JsonConverter.jsonToChatMessage(message.getPayload());

        if (messageSentAllOrSpecificUser(chatMessage)) {
            chatService.sendMessage(chatMessage);
        }
    }

    private boolean messageSentAllOrSpecificUser(ChatMessage chatMessage) {
        return ChatRooms.GENERAL.name().equalsIgnoreCase(chatMessage.getTo()) ||
                webSocketSessionManager.getWebSocketSessions(chatMessage.getTo()) != null;
    }

    private User getUser(WebSocketSession session) {
        return ((SecurityUser) ((UsernamePasswordAuthenticationToken) Objects.requireNonNull(session.getPrincipal()))
                .getPrincipal())
                .getUser();
    }
}
