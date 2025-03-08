package com.kirill.idfc.mapping;

import com.kirill.idfc.dto.task.TaskCreateDTO;
import com.kirill.idfc.dto.task.TaskDTO;
import com.kirill.idfc.entities.TaskEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskMap {
    public static TaskEntity convertToEntity(TaskDTO taskDTO) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDTO.getTitle());
        taskEntity.setId(taskEntity.getId());
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setUser(taskDTO.getUser());
        return taskEntity;
    }

    public static TaskDTO convertToDTO(TaskEntity taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setDescription(taskEntity.getDescription());
        taskDTO.setTitle(taskEntity.getTitle());
        taskDTO.setUser(taskEntity.getUser());
        return taskDTO;
    }
}
