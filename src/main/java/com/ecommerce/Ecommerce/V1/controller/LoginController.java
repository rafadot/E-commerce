package com.ecommerce.Ecommerce.V1.controller;

import com.ecommerce.Ecommerce.V1.dto.LoginRequest;
import com.ecommerce.Ecommerce.V1.service.interfaces.LoginService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest login){
        return new ResponseEntity<>(loginService.authentication(login), HttpStatus.OK);
    }

    @GetMapping("/recovery-password")
    public ResponseEntity<String> recoveryPassword(@RequestParam String email) throws MessagingException, TemplateException, IOException {
        return new ResponseEntity<>(loginService.passwordRecovery(email),HttpStatus.OK);
    }

    @GetMapping("/recovery-password/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email,
                                              @RequestParam int code){
        return new ResponseEntity<>(loginService.verifyCode(email,code),HttpStatus.OK);
    }

    @GetMapping("/recovery-password/change")
    public ResponseEntity<Map<String,String>> passwordRecoveryChange(@RequestParam String email,
                                                                     @RequestParam String password){
        return new ResponseEntity<>(loginService.passwordRecoveryChange(email,password),HttpStatus.OK);
    }
}
