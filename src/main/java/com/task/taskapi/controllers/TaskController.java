package com.task.taskapi.controllers;

import com.task.taskapi.domain.dtos.GeneralResponse;
import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.dtos.task.ResponseTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.service.contrat.TaskService;
import com.task.taskapi.service.contrat.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createTask (@RequestBody @Valid CreateTaskDto task ){
        try{
            User user = userService.findUserAuthenticated();

            Task createdTask = taskService.createTask(task, user);
            return GeneralResponse.getResponse(HttpStatus.CREATED, "Task created successfully", new ResponseTaskDto(createdTask) );
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
