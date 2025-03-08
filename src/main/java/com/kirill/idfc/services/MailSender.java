package com.kirill.idfc.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    private MimeMessageHelper createMessageHelper(MimeMessage mimeMessage, String to, String subject, String text) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(username);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);
        return messageHelper;
    }

    public void sendSimpleMail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        createMessageHelper(mimeMessage, to, subject, text);
        mailSender.send(mimeMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, String text, File file) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = createMessageHelper(mimeMessage, to, subject, text);
        messageHelper.addAttachment(file.getName(), file); // name of file = mail title
        mailSender.send(mimeMessage);
    }
}
