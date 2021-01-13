package com.vu.projectt.service;

import java.util.List;

import com.vu.projectt.model.Role;
import com.vu.projectt.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRolename(roleName);

    }

    public List<Role> findAllRole() {
        return (List<Role>) roleRepository.findAll();
        
    }
    public void add(Role role) {
        roleRepository.save(role);

    }
}
