package com.kirill.idfc.services;

import com.kirill.idfc.entities.TaskEntity;
import com.kirill.idfc.entities.UserEntity;
import com.kirill.idfc.errors.NoSuchException;
import com.kirill.idfc.repositories.TaskRepository;
import com.kirill.idfc.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskEntity getTaskById(int id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    public List<TaskEntity> getTasksByAssignedId(int assignedId) {
        Optional<UserEntity> user = userRepository.findById(assignedId);
        if (user.isEmpty()) {
            throw new NoSuchException("User with id " + assignedId + " does not exist");
        }
        return taskRepository.findByUserId(assignedId);
    }

    public void save(TaskEntity task) {
        taskRepository.save(task);
    }
}
