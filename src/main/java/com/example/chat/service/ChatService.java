package com.example.chat.service;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.User;

import java.util.List;

public interface ChatService {

    /**
     * Получить список сообщений
     *
     * @param chatId Идентификатор чата
     * @param offset Смещение
     * @param size   Количество сообщений
     * @return Список сообщений
     */
    List<ChatMessage> getChatMessages(String chatId, Long offset, Long size);

    /**
     * Отправить сообщение в чат
     *
     * @param message Сообщение
     */
    void sendMessage(ChatMessage message);

    /**
     * Добавть пользователя в список онлайн пользователей
     */
    void userHasEnteredChat(User user);

    /**
     * Убрать пользователя из списка онлайн пользователей
     */
    void userHasLeftChat(User user);
}
