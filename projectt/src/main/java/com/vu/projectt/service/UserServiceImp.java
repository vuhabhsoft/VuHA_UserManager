package com.vu.projectt.service;

import java.util.List;

import com.vu.projectt.model.User;
import com.vu.projectt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
        
    }

    @Override
    public User getUserById(Integer id) {
        
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    @Override
    public void remove(User user) {
        userRepository.delete(user);

    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
    
}
