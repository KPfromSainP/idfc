package com.kirill.idfc.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank(message = "Your name must be not blank")
    @Size(max = 40, message = "Max size of name is 40")
    private String name;

    @Pattern(
            regexp = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",
            message = "Your email is invalid")
    @Size(min = 6, max = 40, message = "Min size of email is 6, max is 20")
    private String email;

    @Size(min = 6, max = 20, message = "Min size of password is 6, max is 20")
    private String password;
}
