package com.kirill.idfc.repositories;

import com.kirill.idfc.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    Optional<TaskEntity> findById(int id);
    List<TaskEntity> findByUserId(int assignedId);

    @Query("SELECT e FROM TaskEntity e WHERE e.user.id != :assignedId")
    List<TaskEntity> getTasksNotAssignedToUser(int assignedId);
}
