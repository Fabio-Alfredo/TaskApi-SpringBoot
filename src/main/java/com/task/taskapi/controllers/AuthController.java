package com.task.taskapi.controllers;

import com.task.taskapi.domain.dtos.GeneralResponse;
import com.task.taskapi.domain.dtos.LoginDto;
import com.task.taskapi.domain.dtos.RegisterDto;
import com.task.taskapi.domain.dtos.TokenDto;
import com.task.taskapi.domain.models.Token;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.service.contrat.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> registerUser (@RequestBody @Valid RegisterDto userDto){
        try{
            userService.createUser(userDto);
            return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered successfully");
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/login")
    public  ResponseEntity<GeneralResponse> loginUser (@RequestBody @Valid LoginDto data, BindingResult validations){
        try{
            Token token = userService.loginUser(data);
            return GeneralResponse.getResponse(HttpStatus.OK, "User logged in successfully", new TokenDto(token) );
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
