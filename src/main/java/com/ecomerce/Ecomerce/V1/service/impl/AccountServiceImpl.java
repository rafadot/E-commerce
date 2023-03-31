package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.PasswordRecovery;
import com.ecomerce.Ecomerce.V1.repository.AccountRepository;
import com.ecomerce.Ecomerce.V1.repository.RoleRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public AccountResponse create(AccountRequest request) {
        if(accountRepository.findByEmail(request.getEmail()).isPresent() && !request.getEmail().equals("test"))
            throw new BadRequestException("E-mail já cadastrado");

        PasswordRecovery passwordRecovery = PasswordRecovery
                .builder()
                .verified(false)
                .build();

        Account account = new Account();
        BeanUtils.copyProperties(request,account);
        account.getRole().add(roleRepository.findByName("USER"));
        account.setPassword(encoder.encode(account.getPassword()));
        account.setPasswordRecovery(passwordRecovery);
        accountRepository.save(account);

        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(account,response,"role");
        response.setRole(account.getRole());
        return response;
    }

    @Override
    public List<AccountResponse> getAll() {
        if(accountContext().getRole().stream().noneMatch(role -> role.getName().toString().equals("ADMIN")))
            throw new BadRequestException("Rota reservada para desenvolvimento. Uso excluiso para ADMS");

        return accountRepository.findAll().stream().map(m -> {
            AccountResponse response = new AccountResponse();
            BeanUtils.copyProperties(m, response);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public Account accountContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null  && !authentication.isAuthenticated())
            throw new BadRequestException("Sua sessão expirou. Por favor faça o login novamente!");

        return accountRepository
                .findByEmail(authentication.getName())
                .orElseThrow(()-> new BadRequestException("Email não cadastrado!"));
    }

    @Override
    public Account accountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty())
            throw new BadRequestException("E-mail não cadastrado!");

        return account.get();
    }

}
