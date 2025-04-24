package com.task.taskapi.service.impl;

import com.task.taskapi.domain.dtos.RegisterDto;
import com.task.taskapi.domain.models.Role;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.repositories.UserRepository;
import com.task.taskapi.service.contrat.RoleService;
import com.task.taskapi.service.contrat.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
     public void createUser(RegisterDto userDto) {
        try{
            User user = modelMapper.map(userDto, User.class);
            if(userRepository.existsUserByEmail(user.getEmail())){
                throw new RuntimeException("User already exists");
            }

            user.setRoles(List.of(roleService.findById("USER")));
            userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException(
                    e.getMessage()!=null ? e.getMessage() : "Error while creating user "
            );

        }
    }

    @Override
    public User findUserByEmail(String email){
        try{
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new RuntimeException("User not found");
            }
            return user;
        }catch (Exception e){
            throw new RuntimeException("Error while fetching user: " + e.getMessage());
        }
    }
}
