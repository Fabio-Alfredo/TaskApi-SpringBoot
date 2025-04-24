package com.task.taskapi.repositories;

import com.task.taskapi.domain.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token>findByUserAndActive(User user, Boolean active);
}
