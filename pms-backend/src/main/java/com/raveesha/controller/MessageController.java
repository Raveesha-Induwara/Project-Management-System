package com.raveesha.controller;

import com.raveesha.model.Message;
import com.raveesha.request.MessageDto;
import com.raveesha.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    
    @PostMapping("/send")
    public ResponseEntity<Message> createMessage(@RequestBody MessageDto messageDto) throws Exception {
        Message newMessage = messageService.sendMessage(
                messageDto.getSenderId(),
                messageDto.getProjectId(),
                messageDto.getContent()
        );
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }
    
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessageByProjectId(projectId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    
}
