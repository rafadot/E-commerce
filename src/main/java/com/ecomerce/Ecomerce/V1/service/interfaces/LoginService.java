package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.LoginRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface LoginService {
    String authentication(LoginRequest loginRequest);
    String passwordRecovery(String email) throws MessagingException, TemplateException, IOException;
    Boolean verifyCode(String email,int code);
    Map<String,String> passwordRecoveryChange(String email,String passWord);
}
