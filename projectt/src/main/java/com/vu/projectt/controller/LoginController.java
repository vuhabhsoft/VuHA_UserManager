package com.vu.projectt.controller;

import com.vu.projectt.model.User;
import com.vu.projectt.repository.UserRepository;
import com.vu.projectt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping(value = { "/", "/login" })
    public String getLogin() {
        return "login";
    }

    @GetMapping("/user")
    public String hello() {
        return "user";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping("/addUser")
    public String register(Model model, @RequestParam String username, String password, String repassword) {
        if (!password.equals(repassword)) {
            model.addAttribute("failed", "Confirm password didn't match!");
            model.addAttribute("user", new User());
            return "register";
        }
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("failed", "Username existed!");
            model.addAttribute("user", new User());
            return "register";
        }
        password = passwordEncoder.encode(password);
        userService.save(new User(username, password,"user"));
        model.addAttribute("success", "Register Success!");
        return "success";
    }
}
