package com.raveesha.service;

import com.raveesha.model.Chat;
import com.raveesha.model.Message;
import com.raveesha.model.User;
import com.raveesha.repo.MessageRepository;
import com.raveesha.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MessageRepository messageRepo;
    
    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId)
                            .orElseThrow(() -> new Exception("User not fount with id: " + senderId));
        Chat chat = projectService.getChatByProjectId(projectId);
        
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepo.save(message);
        
        chat.getMessages().add(savedMessage);
        return savedMessage;
    }
    
    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return  messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
