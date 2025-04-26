package com.task.taskapi.domain.dtos.task;

import jakarta.validation.constraints.NotBlank;

public class CreateDto {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
}
