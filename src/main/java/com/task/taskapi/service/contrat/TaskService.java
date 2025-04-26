package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.task.CreateTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task createTask(CreateTaskDto task, User user);
    List<Task> getAllTasks();
    List<Task>getAllTasksByUser(User user);
    Task getTaskById(String id);

}
