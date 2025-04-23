package com.task.taskapi.service.impl;

import com.task.taskapi.domain.models.Role;
import com.task.taskapi.repositories.RoleRepository;
import com.task.taskapi.service.contrat.RoleService;
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
                throw new RuntimeException("Role not found");
            }
            return role;
        }catch (Exception e){
            throw new RuntimeException("Error while fetching role: " + e.getMessage());
        }
    }
}
