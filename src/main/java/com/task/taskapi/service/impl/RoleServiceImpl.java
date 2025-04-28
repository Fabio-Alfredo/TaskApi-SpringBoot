package com.task.taskapi.service.impl;

import com.task.taskapi.domain.models.Role;
import com.task.taskapi.exceptions.HttpError;
import com.task.taskapi.repositories.RoleRepository;
import com.task.taskapi.service.contrat.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findById(String id) {
        try {
            Role role = roleRepository.findById(id).orElse(null);
            if (role == null) {
                throw new HttpError(HttpStatus.NOT_FOUND, "Role not found");
            }
            return role;
        }catch (HttpError e){
            HttpStatus status = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
            String message = e.getMessage() != null ? e.getMessage() : "Error while role";
            throw new HttpError(status, message);
        }
    }
}
