package com.ecommerce.Ecommerce.V1.service.interfaces;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void passwordRecovery(String to, Map<String,String> properties) throws MessagingException, TemplateException, IOException;
    void sendSimpleEmail(String subject,String to);
}
