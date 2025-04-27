package com.task.taskapi.service.impl;

import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.dtos.task.ResponseTaskDto;
import com.task.taskapi.domain.dtos.task.UpdateTaskDto;
import com.task.taskapi.domain.dtos.user.ResponseUserDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.TaskStatus;
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
    public List<ResponseTaskDto> getAllTasks() {
        try{
            List<Task> tasks = taskRepository.findAll();

            return tasks.stream()
                    .map(task -> modelMapper.map(task, ResponseTaskDto.class))
                    .toList();
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error while getting all tasks"
            );
        }
    }

    @Override
    public List<ResponseTaskDto> getAllTasksByUser(User user) {
        try{
           List<ResponseTaskDto> tasks = taskRepository.findAllByUser(user);
            return tasks;
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error while getting all tasks by user"
            );
        }
    }

    @Override
    public Task getTaskById(UUID id, User user) {
        try{
            Task task = taskRepository.findByIdAndUser(id, user);
            if(task == null){
                throw new RuntimeException("Task not found");
            }
            return task;
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error while getting task by id"
            );
        }
    }

    @Override
    public Task updateStatusTask(UUID id, TaskStatus status, User user) {
        try{
            Task task = taskRepository.findByIdAndUser(id, user);
            if(task == null){
               throw  new RuntimeException("Task not found");
            }
            task.setStatus(status);
            return taskRepository.save(task);
        }catch (Exception e){
            throw  new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error updating task"
            );
        }
    }

    @Override
    public Task updateDataTask(UUID id, UpdateTaskDto taskDto, User user) {
        try{
            Task task = taskRepository.findByIdAndUser(id, user);
            if(task == null){
                throw new RuntimeException("Task not found");
            }
            if(!taskDto.getTitle().isEmpty())
                task.setTitle(taskDto.getTitle());
            if(!taskDto.getDescription().isEmpty())
                task.setDescription(taskDto.getDescription());

            return taskRepository.save(task);
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error updating task"
            );
        }
    }
}
