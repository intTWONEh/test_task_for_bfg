package com.example.chat.config.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Менеджер активных сессий WebSocket
 */
@Component
public class WebSocketSessionManager {
    private final Map<String, WebSocketSession> webSocketSessions = new HashMap<>();

    public void addWebSocketSession(String userId, WebSocketSession webSocketSession) {
        if (this.webSocketSessions.containsKey(userId)) {
            try {
                this.webSocketSessions.get(userId).close();
            } catch (IOException e) {
                //log
                throw new RuntimeException(e);
            }
        }

        this.webSocketSessions.put(userId, webSocketSession);
    }

    public void removeWebSocketSession(String userId) {
        this.webSocketSessions.remove(userId);
    }

    public WebSocketSession getWebSocketSessions(String userId) {
        return this.webSocketSessions.get(userId);
    }

    public List<WebSocketSession> getAllSessions() {
        return new ArrayList<>(this.webSocketSessions.values());
    }
}
