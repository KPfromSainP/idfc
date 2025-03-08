package com.kirill.idfc.services;

import com.kirill.idfc.entities.UserEntity;
import com.kirill.idfc.errors.AlreadyExistException;
import com.kirill.idfc.errors.MailException;
import com.kirill.idfc.errors.NoSuchException;
import com.kirill.idfc.repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private MailSender mailSender;

    public UserEntity getUserByName(String name) {
        List<UserEntity> users = userRepository.findByName(name);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void save(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistException("User with this email already exists");
        }
        userRepository.save(user);
    }

    private String getUserEmail(int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchException("User with id " + id + " does not exist");
        }
        return user.get().getEmail();
    }

    public boolean sendTestMail(int id) {
        String email = getUserEmail(id);
        try {
            mailSender.sendSimpleMail(email, "Test email", "i wanna test this shit 🤟");
        } catch (MessagingException e) {
            throw new MailException(e.getMessage());
        }
        return true;
    }

    public boolean sendAttachMail(int id, File file) {
        String email = getUserEmail(id);
        try {
            mailSender.sendEmailWithAttachment(email, "Test email", "i wanna test this shit 🤟", file);
        } catch (MessagingException e) {
            throw new MailException(e.getMessage());
        }
        return true;
    }
}
