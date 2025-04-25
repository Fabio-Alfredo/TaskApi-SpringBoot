package com.task.taskapi.service.contrat;

import com.task.taskapi.domain.dtos.RegisterDto;
import com.task.taskapi.domain.models.Token;
import com.task.taskapi.domain.models.User;

public interface UserService {
    void createUser(RegisterDto userDto);
    User findUserByEmail(String email);

    //Functions for token validations
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token) ;
    void cleanToken(User user) throws Exception;
    User findUserAuthenticated();
}
