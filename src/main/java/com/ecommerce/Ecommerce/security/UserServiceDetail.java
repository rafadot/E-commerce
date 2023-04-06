package com.ecommerce.Ecommerce.security;

import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.repository.AccountRepository;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceDetail implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(()-> new BadRequestException("E-mail não cadastrado"));

        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles("")
                .build();
    }
}
