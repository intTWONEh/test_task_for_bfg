package com.example.chat.service;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.MessageType;
import com.example.chat.model.User;
import com.example.chat.repository.ChatRepository;
import com.example.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    private UserRepository userRepository;

    private MessageSendingService messageSendingService;

    @Override
    public List<ChatMessage> getChatMessages(String chatId, Long offset, Long size) {
        return chatRepository.getChatMessages(chatId, offset, size);
    }

    @Override
    public void sendMessage(ChatMessage message) {
        messageSendingService.sendMessage(message);
        chatRepository.saveMessage(message);
    }

    @Override
    public void userHasEnteredChat(User user) {
        userRepository.addInListUserOnline(user);
        informEveryoneAbout(user, MessageType.USER_ONLINE);
    }

    @Override
    public void userHasLeftChat(User user) {
        userRepository.removeFromUserListOnline(user);
        informEveryoneAbout(user, MessageType.USER_OFFLINE);
    }

    private void informEveryoneAbout(User user, MessageType messageType) {
        ChatMessage userOnlineMessage = new ChatMessage();
        userOnlineMessage.setMessageType(messageType);
        userOnlineMessage.setFrom(user.getUserId());
        messageSendingService.sendMessage(userOnlineMessage);
    }
}
