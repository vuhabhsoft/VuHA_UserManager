package com.vu.projectt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Table(name = "role")
@Data
public class Role {
    @Id
    private String rolename;
    @Column(name = "roledescription")
    private String roleDescription;
    @Column(name = "createroleuser")
    private Boolean createRoleUser;
    @Column(name = "updateuser")
    private Boolean updateUser;
    @Column(name = "deleteuser")
    private Boolean deleteUser;
}
