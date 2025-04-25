package com.task.taskapi.controllers;

import com.task.taskapi.domain.dtos.GeneralResponse;
import com.task.taskapi.service.contrat.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse>findRoleById(@PathVariable String roleId){
        try{
            var role = roleService.findById(roleId);
            return GeneralResponse.getResponse(HttpStatus.ACCEPTED, "Role fetched successfully", role);
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.BAD_REQUEST, "Error while fetching role: " + e.getMessage());
        }
    }

}
