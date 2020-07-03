package com.trunghieu.api.auth;


import com.trunghieu.common.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

/**
 * Created on 15/6/2020.
 * Class: EmailSenderService.java
 * By : Admin
 */
@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String userEmail, String confirmationToken){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Account Activation!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/auth/confirm-account?token="+ confirmationToken
                + "   Note: This link will expire after 10 minutes.");
        mailMessage.setFrom("no-reply@trunghieuit.com");
        javaMailSender.send(mailMessage);
    }

    public boolean sendSimpleMail(String to, String sub, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(sub);
        mailMessage.setText(body);
        mailMessage.setFrom("no-reply@trunghieuit.com");
        boolean isSent = false;
        try
        {
            javaMailSender.send(mailMessage);
            isSent = true;
        }
        catch (Exception e) {
            return isSent;
        }

        return isSent;
    }

}
