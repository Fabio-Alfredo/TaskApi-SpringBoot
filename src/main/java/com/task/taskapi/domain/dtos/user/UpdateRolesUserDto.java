package com.task.taskapi.domain.dtos.user;

import com.task.taskapi.domain.enums.UserRoleAction;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRolesUserDto {
    @NotBlank(message = "Role is required")
    private String role;
    @NotBlank(message = "Action is required")
    private UserRoleAction action;

}
