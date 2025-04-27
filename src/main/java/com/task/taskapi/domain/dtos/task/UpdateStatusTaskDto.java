package com.task.taskapi.domain.dtos.task;

import com.task.taskapi.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatusTaskDto {
    @NotBlank(message = "Status is required")
    private TaskStatus status;
}
