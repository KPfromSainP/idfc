package com.kirill.idfc.services;

import com.kirill.idfc.entities.UserEntity;
import com.kirill.idfc.errors.AlreadyExistException;
import com.kirill.idfc.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
