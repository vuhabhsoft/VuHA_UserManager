package com.vu.projectt.service;

import java.util.List;

import com.vu.projectt.model.User;


public interface UserService {
    
    List<User> getAllUser();

    User getUserById(Integer id);

    void save(User user);

    void remove(User user);

    void deleteById(int id);

    User findByUserName(String userName);
}
