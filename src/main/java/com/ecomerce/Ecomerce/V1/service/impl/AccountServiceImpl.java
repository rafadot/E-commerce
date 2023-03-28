package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.account.AccountRequest;
import com.ecomerce.Ecomerce.V1.dto.account.AccountResponse;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.PasswordRecovery;
import com.ecomerce.Ecomerce.V1.model.enums.Role;
import com.ecomerce.Ecomerce.V1.repository.AccountRepository;
import com.ecomerce.Ecomerce.V1.repository.PasswordRecoveryRepository;
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

    @Override
    public AccountResponse create(AccountRequest request) {
        if(accountRepository.findByEmail(request.getEmail()).isPresent())
            throw new BadRequestException("E-mail já cadastrado");

        PasswordRecovery passwordRecovery = PasswordRecovery
                .builder()
                .verified(false)
                .build();

        Account account = new Account();
        BeanUtils.copyProperties(request,account);
        account.setRole(Role.USER);
        account.setPassword(encoder.encode(account.getPassword()));
        account.setPasswordRecovery(passwordRecovery);
        accountRepository.save(account);

        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(account,response);

        return response;
    }

    @Override
    public List<AccountResponse> getAll() {
        if(!accountContext().getRole().toString().equals("ADMIN"))
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
    public String selectRole(Role role) {
        Account account = accountContext();
        account.setRole(role);
        String name = account.getFullName().split(" ")[0];

        accountRepository.save(account);
        return "Role de " + name + " alterada para " + role.toString() + " com sucesso!";
    }

    @Override
    public Account accountByEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty())
            throw new BadRequestException("E-mail inválido");

        return account.get();
    }

}
