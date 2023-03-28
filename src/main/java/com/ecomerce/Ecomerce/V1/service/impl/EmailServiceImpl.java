package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.service.interfaces.EmailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Configuration fmConfiguration;

    @Override
    public void passwordRecovery(String to, Map<String, String> properties) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("Redefinir Senha");
        helper.setTo(to);
        helper.setText(getContentTemplate(properties),true);
        javaMailSender.send(helper.getMimeMessage());
    }

    @Override
    public void sendSimpleEmail(String subject, String to) {
        SimpleMailMessage email =new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        javaMailSender.send(email);
    }

    private String getContentTemplate(Map<String, String> model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-passworod-recovery.html"), model);
    }
}
