package com.task.taskapi.domain.dtos.task;

import com.task.taskapi.domain.dtos.user.ResponseUserDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseTaskDto {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private ResponseUserDto user;

    public ResponseTaskDto(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.user = new ResponseUserDto(task.getUser());
    }
}
