package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    // 1
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account newAccount = accountService.persistAccount(account);
        return ResponseEntity.status(200)
                .body(newAccount);
    }

    // 2
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
        return ResponseEntity.status(200).body(loggedIn);
    }

    // 3 Create a message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message msg = messageService.createMessage(message);
        return ResponseEntity.status(200).body(msg);
    }


    // 4 get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> list = messageService.getMessageList();
        return ResponseEntity.status(200)
            .body(list);
    }

    // 5 get message by id
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") Integer messageId){
        Message msg = messageService.findMessage(messageId);
        return ResponseEntity.status(200)
            .body(msg);
    }

    // 6 delete message by id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("messageId") Integer messageId) {
        int rowsAffected = messageService.deleteMessageById(messageId);
        if(rowsAffected == 0){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(rowsAffected); 
    }

    // 7 update message by id
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable("messageId") Integer messageId, @RequestBody String messageText) {
        int rowsAffected = messageService.updateMessageById(messageId, messageText);
        return ResponseEntity.status(200).body(rowsAffected);
    }

    // 8 retreive all messages from user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable("accountId") Integer accountId){
        List<Message> list = messageService.getMessageListFromUser(accountId);
        return ResponseEntity.status(200).body(list);
    }
}
