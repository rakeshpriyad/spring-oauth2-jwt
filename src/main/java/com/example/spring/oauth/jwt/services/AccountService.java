package com.example.spring.oauth.jwt.services;

import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.oauth.jwt.models.Account;
import com.example.spring.oauth.jwt.models.Role;
import com.example.spring.oauth.jwt.repositories.AccountRepo;

@Service
public class AccountService implements UserDetailsService {

    private final Logger logger = Logger.getLogger("AccountService.class");

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername( s );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
        }
    }

    public Account findAccountByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUsername( username );
        if ( account.isPresent() ) {
            return account.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    public Account registerUser(Account account) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.grantAuthority(Role.ROLE_USER);
            return accountRepo.save( account );
    }

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public boolean removeAuthenticatedAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account acct = findAccountByUsername(username);
        int del = accountRepo.deleteAccountById(acct.getId());
        return del > 0;
    }
}
