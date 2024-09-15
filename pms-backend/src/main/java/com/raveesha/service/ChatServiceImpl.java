package com.raveesha.service;

import com.raveesha.model.Chat;
import com.raveesha.repo.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepo;
    
    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
