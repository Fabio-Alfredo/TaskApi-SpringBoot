package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.RegisterDto;

public interface UserService {
    void createUser(RegisterDto userDto);

}
