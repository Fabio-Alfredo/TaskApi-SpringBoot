package com.task.taskapi.controllers;

import com.task.taskapi.domain.dtos.GeneralResponse;
import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.dtos.task.ResponseTaskDto;
import com.task.taskapi.domain.dtos.task.UpdateStatusTaskDto;
import com.task.taskapi.domain.dtos.task.UpdateTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.service.contrat.TaskService;
import com.task.taskapi.service.contrat.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/myTasks")
    public ResponseEntity<GeneralResponse>findAllMyTasks(){
        try{
            User user = userService.findUserAuthenticated();
            List<ResponseTaskDto>tasks = taskService.getAllTasksByUser(user);
            return  GeneralResponse.getResponse(HttpStatus.OK, "All task created", tasks);
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<GeneralResponse>findTaskByIdAndUser(@PathVariable UUID taskId){
        try{
            User user = userService.findUserAuthenticated();
            Task task = taskService.getTaskById(taskId, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Task created", new ResponseTaskDto(task));
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse>findAllTasks (){
        try{
            List<ResponseTaskDto> tasks = taskService.getAllTasks();
            return GeneralResponse.getResponse(HttpStatus.OK, "All tasks created", tasks);
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/status/{taskId}")
    public  ResponseEntity<GeneralResponse> updateStatusInTask(@PathVariable UUID taskId, @RequestBody UpdateStatusTaskDto status){
        try{
            User user = userService.findUserAuthenticated();
            Task task = taskService.updateStatusTask(taskId, status.getStatus(),user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Updated status in task", new ResponseTaskDto(task));
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<GeneralResponse>updateDataInTask(@PathVariable UUID taskId, @RequestBody UpdateTaskDto taskDto){
        try{
            User user = userService.findUserAuthenticated();
            Task task = taskService.updateDataTask(taskId, taskDto, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Update data by task", new ResponseTaskDto(task));
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
