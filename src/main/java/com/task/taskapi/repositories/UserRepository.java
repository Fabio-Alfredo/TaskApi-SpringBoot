package com.task.taskapi.repositories;

import com.task.taskapi.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Boolean existsUserByEmail(String email);
    User findByEmail(String email);
}
