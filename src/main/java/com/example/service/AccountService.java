package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getAccountByUsername(String user){
        return accountRepository.findByUsername(user);
    }

    public Account login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    // public Account getAccountByUsernameAndPassword(String user, String password){
    //     return accountRepository.getByUser
    // }
}
