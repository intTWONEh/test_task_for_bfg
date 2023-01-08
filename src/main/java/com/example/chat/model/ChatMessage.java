package com.example.chat.model;

import lombok.Data;

@Data
public class ChatMessage {
    private String from;
    private String to;
    private String text;
    private String chatId;
    private Long time;
    private MessageType messageType;
}
