package com.task.taskapi.domain.dtos.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskDto {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
}
