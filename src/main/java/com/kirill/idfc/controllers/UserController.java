package com.kirill.idfc.controllers;

import com.kirill.idfc.dto.user.UserCreateDTO;
import com.kirill.idfc.dto.user.UserDTO;
import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.mapping.UserCreateMap;
import com.kirill.idfc.mapping.UserMap;
import com.kirill.idfc.services.ExcelGenerator;
import com.kirill.idfc.services.TaskService;
import com.kirill.idfc.services.UserService;
import com.kirill.idfc.entities.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/users/{name}")
    public UserDTO getUser(@PathVariable @NotBlank String name) {
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

    @PostMapping("/users/test_mail/{userId}")
    public boolean testMail(@PathVariable int userId) {
        List<TaskEntity> tasks = taskService.getTasksByAssignedId(userId);
        return userService.sendTestMail(userId, tasks);
    }

    @PostMapping("/users/test_attach_mail/{userId}")
    public boolean testAttachMail(@PathVariable int userId) {
        List<TaskEntity> tasks = taskService.getAllTasks();
        byte[] excelBytes = ExcelGenerator.generateExcel(tasks, taskService.getTasksByAssignedId(userId), taskService.getTasksNotAssignedToUser(userId), userService.getUserById(userId));
        if (excelBytes.length != 0) {
            String fileName = "example.xls";
            File file = new File(fileName);
            try {
                FileUtils.writeByteArrayToFile(file, excelBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return userService.sendAttachMail(userId, file, tasks);
        }
        return userService.sendTestMail(userId, tasks);
    }
}
