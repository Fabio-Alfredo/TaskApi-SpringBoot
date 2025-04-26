package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.dtos.task.ResponseTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task createTask(CreateTaskDto task, User user);
    List<ResponseTaskDto> getAllTasks();
    List<ResponseTaskDto>getAllTasksByUser(User user);
    Task getTaskById(UUID id, User user);

}
