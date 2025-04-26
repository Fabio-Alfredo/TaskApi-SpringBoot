package com.task.taskapi.service.impl;

import com.task.taskapi.domain.dtos.auth.LoginDto;
import com.task.taskapi.domain.dtos.auth.RegisterDto;
import com.task.taskapi.domain.models.Token;
import com.task.taskapi.domain.models.User;
import com.task.taskapi.repositories.TokenRepository;
import com.task.taskapi.repositories.UserRepository;
import com.task.taskapi.service.contrat.RoleService;
import com.task.taskapi.service.contrat.UserService;
import com.task.taskapi.utils.JWTTools;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final JWTTools jwtTools;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, JWTTools jwtTools, TokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.jwtTools = jwtTools;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createUser(RegisterDto userDto) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            User user = modelMapper.map(userDto, User.class);
            if (userRepository.existsUserByEmail(user.getEmail())) {
                throw new RuntimeException("User already exists");
            }

            user.setRoles(List.of(roleService.findById("USER")));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(
                    e.getMessage() != null ? e.getMessage() : "Error while creating user "
            );

        }
    }

    @Override
    public Token loginUser(LoginDto data){
        try{
            User user = userRepository.findByEmail(data.getEmail());
            if(user == null || !passwordEncoder.matches(data.getPassword(), user.getPassword())){
                throw new RuntimeException("Invalid credentials");
            }

            Token token = registerToken(user);

            return token;
        }catch (Exception e){
            throw new RuntimeException("Error while logging in user: " + e.getMessage());

        }
    }


    @Override
    public User findUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user: " + e.getMessage());
        }
    }

    @Override
    public User findById(UUID id){
        try{
            User user = userRepository.findById(id).orElse(null);
            if(user == null){
                throw new RuntimeException("User not found");
            }
            return user;
        }catch (Exception e){
            throw new RuntimeException("Error while fetching user: " + e.getMessage());
        }
    }

    //Method for validation auth user
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) throws Exception {
        cleanToken(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);

        tokenRepository.save(token);
        return token;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanToken(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream()
                    .filter(t -> t.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new Exception());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error while validating token: " + e.getMessage());
        }
    }



    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanToken(User user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(token ->{
            if(!jwtTools.verifyToken(token.getContent())){
                token.setActive(false);
                tokenRepository.save(token);
            }
        });
    }

    @Override
    public User findUserAuthenticated() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email);
    }

}
