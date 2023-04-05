package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.LoginRequest;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.repository.AccountRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import com.ecomerce.Ecomerce.V1.service.interfaces.EmailService;
import com.ecomerce.Ecomerce.V1.service.interfaces.LoginService;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import com.ecomerce.Ecomerce.security.JwtService;
import com.ecomerce.Ecomerce.security.UserServiceDetail;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserServiceDetail userDetail;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AccountRepository accountRepository;
    private final EmailService emailService;
    private final AccountService accountService;

    @Override
    public String authentication(LoginRequest login) {
        UserDetails user = userDetail.loadUserByUsername(login.getEmail());

        if(!encoder.matches(login.getPassword(),user.getPassword()))
            throw new BadRequestException("Senha inválida");

        Account account = Account
                .builder()
                .email(user.getUsername())
                .password(user.getPassword())
                .build();

        return jwtService.generateToken(account);
    }

    @Override
    public String passwordRecovery(String email) throws MessagingException, TemplateException, IOException {
        Account account = accountService.accountByEmail(email);

        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        LocalDateTime actual = LocalDateTime.now();

        account.getPasswordRecovery().setCode(code);
        account.getPasswordRecovery().setExpiration(actual.plusMinutes(5));

        accountRepository.save(account);

        Map<String,String> propertiesEmail = new HashMap<>();
        propertiesEmail.put("accountName",account.getFullName().split(" ")[0]);
        propertiesEmail.put("text","Foi feito uma solicitação de alteração de senha na sua conta, seu código é ");
        propertiesEmail.put("code",String.valueOf(code));
        emailService.passwordRecovery(email,propertiesEmail);
        return "E-mail enviado para " + email + " com sucesso!";
    }

    @Override
    public Boolean verifyCode(String email,int code) {
        Account account = accountService.accountByEmail(email);

        if(account.getPasswordRecovery().getCode() == null)
            throw new BadRequestException("Você não possui um código de verificação, por favor solicite um");

        if(account.getPasswordRecovery().getCode() != code)
            throw new BadRequestException("Código inválido!");

        if(account.getPasswordRecovery().getExpiration().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Código expirado, por favor solicite o código de verificação novamente!");

        account.getPasswordRecovery().setVerified(true);
        accountRepository.save(account);

        return true;
    }

    @Override
    public Map<String, String> passwordRecoveryChange(String email, String passWord) {
        Account account = accountService.accountByEmail(email);

        if(account.getPasswordRecovery().getCode() == null)
            throw new BadRequestException("Você não possui um código de verificação, por favor solicite um!");

        if(account.getPasswordRecovery().getExpiration().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Código expirado, solicite novamente.");

        if(!account.getPasswordRecovery().getVerified())
            throw new BadRequestException("Algo deu errado, por favor solicite o código de verificação novamente!");

        account.setPassword(encoder.encode(passWord));
        account.getPasswordRecovery().setCode(null);
        account.getPasswordRecovery().setExpiration(null);
        account.getPasswordRecovery().setVerified(false);
        accountRepository.save(account);
        LoginRequest login = LoginRequest
                .builder()
                .email(account.getEmail())
                .password(passWord)
                .build();
        String token = authentication(login);

        Map<String,String> response = new HashMap<>();
        response.put("message",account.getFullName().split(" ")[0] + " sua senha foi alterada com sucesso! Você será redirecionado para página principal.");
        response.put("token",token);

        return response;
    }
}
