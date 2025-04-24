package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.RegisterDto;
import com.task.taskapi.domain.models.User;

public interface UserService {
    void createUser(RegisterDto userDto);
    User findUserByEmail(String email);

}
