package com.kirill.idfc.controllers;

import com.kirill.idfc.dto.task.TaskCreateDTO;
import com.kirill.idfc.dto.task.TaskDTO;
import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.mapping.TaskCreateMap;
import com.kirill.idfc.mapping.TaskMap;
import com.kirill.idfc.services.ExcelGenerator;
import com.kirill.idfc.services.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Validated
@RestController
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/{id}")
    public TaskDTO getTask(@PathVariable int id) {
        TaskEntity taskEntity = taskService.getTaskById(id);
        if (taskEntity == null) {
            return null;
        }
        return TaskMap.convertToDTO(taskEntity);
    }

    @PostMapping("/task/")
    public int addTask(@Valid @RequestBody TaskCreateDTO task) {
        TaskEntity taskEntity = TaskCreateMap.convertToEntity(task);
        taskService.save(taskEntity);
        TaskDTO taskDTO = TaskMap.convertToDTO(taskEntity);
        return taskDTO.getId();
    }

    @GetMapping("/task/generate_excel/{userId}")
    public ResponseEntity<Resource> downloadUsersTasks(@PathVariable int userId) {
        byte[] excelBytes = ExcelGenerator.generateExcel(taskService.getTasksByAssignedId(userId));
        if (excelBytes.length != 0) {
            String fileName = "example.xls";
            MediaType mediaType = MediaType.parseMediaType("application/vnd.ms-excel");
            File file = new File(fileName);
            try {
                FileUtils.writeByteArrayToFile(file, excelBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            InputStreamResource resource;
            try {
                resource = new InputStreamResource(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(resource);
        } else {
            return null;
        }
    }
}
