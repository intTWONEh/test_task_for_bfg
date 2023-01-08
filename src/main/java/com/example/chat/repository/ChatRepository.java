package com.example.chat.repository;

import com.example.chat.model.ChatMessage;

import java.util.List;

public interface ChatRepository {
    List<ChatMessage> getChatMessages(String chatId, Long offset, Long size);

    void saveMessage(ChatMessage message);
}
