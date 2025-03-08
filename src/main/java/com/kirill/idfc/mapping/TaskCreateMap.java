package com.kirill.idfc.mapping;

import com.kirill.idfc.dto.task.TaskCreateDTO;
import com.kirill.idfc.dto.user.UserCreateDTO;
import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.entities.UserEntity;
import org.springframework.stereotype.Service;


@Service
public class TaskCreateMap {
    public static TaskEntity convertToEntity(TaskCreateDTO taskCreateDTO) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskCreateDTO.getTitle());
        taskEntity.setDescription(taskCreateDTO.getDescription());
        taskEntity.setUser(taskCreateDTO.getUser());
        return taskEntity;
    }

    public static TaskCreateDTO convertToDTO(TaskEntity taskEntity) {
        TaskCreateDTO taskDTO = new TaskCreateDTO();
        taskDTO.setDescription(taskEntity.getDescription());
        taskDTO.setTitle(taskEntity.getTitle());
        taskDTO.setUser(taskEntity.getUser());
        return taskDTO;
    }
}
