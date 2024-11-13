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

    public List<Message> getMessageListFromUser(Integer postedBy) {
        return (List<Message>) messageRepository.findByPostedBy(postedBy);
    }
    
    public Message findMessage(Integer id) {
        return messageRepository.findByMessageId(id);
    }

    public int deleteMessageById(Integer id) {
        Message exists = messageRepository.findByMessageId(id);
        if (exists != null) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text must be non-blank and under 255 characters.");
        }
        accountRepository.findById(message.getPostedBy())
            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + message.getPostedBy() + " does not exist."));
        
        return messageRepository.save(message);
    }

    public int updateMessageById(Integer id, String newMessage) {
        // Ensure the message is not null or empty first
        if (newMessage == null || newMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be blank.");
        }
        
        // Check the length constraint
        if (newMessage.length() > 255) {
            throw new IllegalArgumentException("Message length exceeds 255 characters.");
        }
    
        // Proceed with fetching the message and updating it
        Message exists = messageRepository.findByMessageId(id);
        if (exists == null) {
            throw new ResourceNotFoundException("Could not find message with id: " + id + ".");
        }
        
        // Update the message
        exists.setMessageText(newMessage);
        messageRepository.save(exists);
        return 1; // Indicating one row was modified
    }
    
}
