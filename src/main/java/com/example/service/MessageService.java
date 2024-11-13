package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
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
}
