package com.kirill.idfc.mapping;

import com.kirill.idfc.dto.user.UserCreateDTO;
import com.kirill.idfc.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserCreateMap {
    public static UserEntity convertToEntity(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userCreateDTO.getName());
        userEntity.setEmail(userCreateDTO.getEmail());
        userEntity.setPassword(userCreateDTO.getPassword());
        return userEntity;
    }

    public static UserCreateDTO convertToDTO(UserEntity userEntity) {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }
}
