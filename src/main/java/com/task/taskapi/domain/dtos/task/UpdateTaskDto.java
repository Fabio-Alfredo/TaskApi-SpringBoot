package com.task.taskapi.domain.dtos.task;

import lombok.Data;

@Data
public class UpdateTaskDto {
    private String title;
    private String description;
}
