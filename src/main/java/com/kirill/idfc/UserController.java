package com.kirill.idfc;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{name}")
    public User hello(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @PostMapping("/users/")
    public int addUser(@RequestBody User user) {
        userService.save(user);
        return user.getId();
    }
}
