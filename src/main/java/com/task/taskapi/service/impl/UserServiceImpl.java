package com.task.taskapi.service.impl;

import com.task.taskapi.domain.dtos.RegisterDto;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.repositories.UserRepository;
import com.task.taskapi.service.contrat.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createUser(RegisterDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        System.out.println("User to be saved: " + user);
        userRepository.save(user);
    }
}
