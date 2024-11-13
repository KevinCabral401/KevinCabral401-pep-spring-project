package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getMessageList() {
        return (List<Message>) messageRepository.findAll();
    }
    
    public Message findMessage(Integer id) throws ResourceNotFoundException {
        return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message with Id " + id + " was not found"));
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public Message createMessage(Message message) {
        // Validate message text
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text must be non-blank and under 255 characters.");
        }
        // Validate postedBy user
        accountRepository.findById(message.getPostedBy())
            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + message.getPostedBy() + " does not exist."));
        
        return messageRepository.save(message);
    }
}
