package com.medibook.notification.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailManager {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message,true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("",body);
            mailSender.send(message);
        }
        catch(MessagingException e){
            throw new RuntimeException(e);
        }
    }
}


