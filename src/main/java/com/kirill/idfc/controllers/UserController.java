package com.kirill.idfc.controllers;

import com.kirill.idfc.dto.user.UserCreateDTO;
import com.kirill.idfc.dto.user.UserDTO;
import com.kirill.idfc.mapping.UserCreateMap;
import com.kirill.idfc.mapping.UserMap;
import com.kirill.idfc.services.UserService;
import com.kirill.idfc.entities.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{name}")
    public UserDTO hello(@PathVariable @NotBlank String name) {
        UserEntity userEntity = userService.getUserByName(name);
        return UserMap.convertToDTO(userEntity);
    }

    @PostMapping("/users/")
    public int addUser(@Valid @RequestBody UserCreateDTO user) {
        UserEntity userEntity = UserCreateMap.convertToEntity(user);
        userService.save(userEntity);
        UserDTO userDTO = UserMap.convertToDTO(userEntity);
        return userDTO.getId();
    }
}
