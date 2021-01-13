package com.vu.projectt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private  String username;
    private  String password;
    private String fullname;
    private Integer age;
    private String interest;
    private String sex;
    private String other;
    @Column(columnDefinition = "varchar(16) default 'USER'")
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role=role;
    }
    
    public User() {
    }

    public User(Integer id, String username, String fullname, Integer age, String interest, String sex, String other,
            String role) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.age = age;
        this.interest = interest;
        this.sex = sex;
        this.other = other;
        this.role = role;
    }

}
