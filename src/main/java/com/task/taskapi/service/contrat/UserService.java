package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.auth.LoginDto;
import com.task.taskapi.domain.dtos.auth.RegisterDto;
import com.task.taskapi.domain.dtos.user.AddRoleDto;
import com.task.taskapi.domain.dtos.user.ResponseUserDto;
import com.task.taskapi.domain.models.Token;
import com.task.taskapi.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(RegisterDto userDto);
    User findUserByEmail(String email);
    User findById(UUID id);
    List<ResponseUserDto>findAllUsers();
    User updateRolesInUser(UUID userId, String newRole);


    //Function for login user
    Token loginUser(LoginDto data) throws Exception;

    //Functions for token validations
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token) ;
    void cleanToken(User user) throws Exception;
    User findUserAuthenticated();
}
