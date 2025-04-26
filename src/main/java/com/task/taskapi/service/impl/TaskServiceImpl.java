package com.task.taskapi.service.impl;

import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.repositories.TaskRepository;
import com.task.taskapi.service.contrat.TaskService;
import com.task.taskapi.service.contrat.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Task createTask(CreateTaskDto task, User user) {
        try{
            Task taskEntity = modelMapper.map(task, Task.class);
            taskEntity.setUser(user);

            return taskRepository.save(taskEntity);
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error while creating task "
            );
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public List<Task> getAllTasksByUser(User user) {
        return List.of();
    }

    @Override
    public Task getTaskById(String id) {
        return null;
    }
}
