package com.kirill.idfc.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
}
