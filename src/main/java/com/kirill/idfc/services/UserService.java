package com.kirill.idfc.services;

import com.kirill.idfc.entities.UserEntity;
import com.kirill.idfc.errors.AlreadyExistException;
import com.kirill.idfc.errors.NoSuchException;
import com.kirill.idfc.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private MailSenderIm mailSender;

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

    public boolean sendTestMail(int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchException("No such user");
        }
        String email = user.get().getEmail();
        mailSender.sendSimpleMail(email, "Test email", "i wanna test this shit 🤟");
        return true;
    }
}
