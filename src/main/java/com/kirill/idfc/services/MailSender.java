package com.kirill.idfc.services;

import com.kirill.idfc.entities.TaskEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    private MimeMessageHelper createMessageHelper(MimeMessage mimeMessage, String to, String subject, String userName, List<TaskEntity> tasks) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(username);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        String emailContent = getActivationEmailContent(userName, tasks);
        messageHelper.setText(emailContent, true);
        return messageHelper;
    }

    public void sendSimpleMail(String to, String userName, String subject, List<TaskEntity> tasks) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        createMessageHelper(mimeMessage, to, subject, userName, tasks);
        mailSender.send(mimeMessage);
    }

    public void sendEmailWithAttachment(String to, String userName, String subject, File file, List<TaskEntity> tasks) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = createMessageHelper(mimeMessage, to, subject, userName, tasks);
        messageHelper.addAttachment(file.getName(), file);
        mailSender.send(mimeMessage);
    }

    private String getActivationEmailContent(String userName, List<TaskEntity> tasks) {
        // будем передавать готовый Context чтобы не ебаться с огромным колвом аргументов getActivationEmailContent
        Context context = new Context();
//        context.setVariable("userName", userName);
        context.setVariable("tasks", tasks);
        return templateEngine.process("more-beauty", context);
//        return templateEngine.process("email-template", context);
    }
}
