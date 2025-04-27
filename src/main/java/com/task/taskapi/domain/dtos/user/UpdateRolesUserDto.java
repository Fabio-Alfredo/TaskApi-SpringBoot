package com.task.taskapi.domain.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddRoleDto {
    @NotBlank(message = "Role is required")
    private String role;
}
