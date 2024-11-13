package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UnauthorizedException;
import com.example.exception.UserExistsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account login(String username, String password){
        if(accountRepository.findByUsernameAndPassword(username, password) == null){
            throw new UnauthorizedException("Invalid Username or Password.");
        }
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public Account persistAccount(Account account){
        if(accountRepository.findByUsername(account.getUsername())!=null){
            throw new UserExistsException("This Username already exists");
        }
        return accountRepository.save(account);
    }

}
