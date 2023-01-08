package com.example.chat.controller;

import com.example.chat.model.ChatMessage;

import com.example.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;

    @GetMapping("/{chatId}")
    public List<ChatMessage> getMessage(
            @PathVariable String chatId,
            @RequestParam(defaultValue = "0") Long offset,
            @RequestParam(defaultValue = "-1") Long size
    ) {
        return chatService.getChatMessages(chatId, offset, size);
    }

}
