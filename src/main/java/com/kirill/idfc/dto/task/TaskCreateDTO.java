package com.kirill.idfc.dto.task;

import com.kirill.idfc.entities.UserEntity;
import lombok.Data;

@Data
public class TaskCreateDTO {
    private String title;
    private String description;
    private UserEntity user;
    private int price;
}
