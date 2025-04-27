package com.task.taskapi.domain.dtos.task;

import com.task.taskapi.domain.models.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskDto {
    private String title;
    private String description;
}
