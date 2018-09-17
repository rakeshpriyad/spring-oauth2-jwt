package com.example.spring.oauth.jwt.repositories;


import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.spring.oauth.jwt.models.Account;

public interface AccountRepo extends Repository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Account save(Account account);
    int deleteAccountById(Long id);
}
