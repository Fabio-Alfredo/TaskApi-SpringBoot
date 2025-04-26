package com.task.taskapi.repositories;

import com.task.taskapi.domain.dtos.task.ResponseTaskDto;
import com.task.taskapi.domain.models.Task;
import com.task.taskapi.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<ResponseTaskDto> findAllByUser(User user);
    Task findByIdAndUser(UUID id, User user);
}
