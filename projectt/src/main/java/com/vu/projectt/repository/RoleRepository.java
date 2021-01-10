package com.vu.projectt.repository;

import com.vu.projectt.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends  JpaRepository<Role, String>{
    Role findByRolename(String rolename);
}
