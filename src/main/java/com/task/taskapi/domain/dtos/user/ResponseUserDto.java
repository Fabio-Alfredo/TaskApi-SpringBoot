package com.task.taskapi.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.task.taskapi.domain.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
public class ResponseUserDto {
    private UUID id;
    private String name;
    private String email;

    public ResponseUserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
