package com.task.taskapi.controllers;

import com.task.taskapi.domain.dtos.GeneralResponse;
import com.task.taskapi.domain.dtos.user.ResponseUserDto;
import com.task.taskapi.domain.dtos.user.UpdateRolesUserDto;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.service.contrat.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse> findAllUsers (){
        try{
            List<ResponseUserDto> users = userService.findAllUsers();
            return GeneralResponse.getResponse(HttpStatus.OK, "All users registered", users);
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{userId}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse>findUserById(@PathVariable UUID userId){
        try{
            User user = userService.findById(userId);
            return GeneralResponse.getResponse(HttpStatus.OK, "Find user", new ResponseUserDto(user));
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/update-roles/{userId}")
        @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse>updateRolesInUser(@PathVariable UUID userId, @RequestBody UpdateRolesUserDto data){
        try{
            User user = userService.updateRolesInUser(userId, data.getRole(), data.getAction());
            return GeneralResponse.getResponse(HttpStatus.OK, "Updating roles", new ResponseUserDto(user));
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

