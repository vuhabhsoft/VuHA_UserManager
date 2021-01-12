package com.vu.projectt.controller;

import com.vu.projectt.model.Role;
import com.vu.projectt.model.User;
import com.vu.projectt.repository.RoleRepository;
import com.vu.projectt.repository.UserRepository;
import com.vu.projectt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    User user;

    @Autowired
    Role role;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = {"/checkLogin"})
    public String checkLogin(Authentication authentication){
        String name = authentication.getName();
        user = userRepository.findByUsername(name);
        role = roleRepository.findByRolename(user.getRole());  
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String findAll(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("name", "Hello " + user.getFullname());
        return "home";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if(!role.getDeleteUser()){
            model.addAttribute("failed", "You dont have the right to delete user!");
            return "fail";
        }
        userService.deleteById(id);
        return "redirect:/home";
    }
    @RequestMapping("/user/edit/{id}")
    public String edit(Model model ,@PathVariable Integer id) {
        if(!role.getUpdateUser()){
            model.addAttribute("failed", "You dont have the right to edit user!");
            return "fail";
        }
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roleName", roleRepository.findAll());
        return "editForm";
    }
    @RequestMapping("/saveUser")
    public String saveUser(Model model,@ModelAttribute User user,@RequestParam String password, String repassword) {
        
        if (!password.equals(repassword)) {
            model.addAttribute("failed", "Confirm password didn't match!");
            return "editForm";           
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/user/detail/" + user.getId();
    }

    @RequestMapping("/user/detail/{id}")
    public String detail(Model model ,@PathVariable Integer id) {
        model.addAttribute("user",userService.getUserById(id));
        return "viewDetail";
    }

    @RequestMapping("/createRole")
    public String createRole(Model model) {
        if(!role.getCreateRoleUser()){
            model.addAttribute("failed", "You dont have the right to create role!");
            return "fail";
        }
        model.addAttribute("role", new Role());
        return "createRole";
    }
    @RequestMapping("/addRole")
    public String addRole(Model model, @ModelAttribute Role role){
        if (roleRepository.findByRolename(role.getRolename()) != null) {
            model.addAttribute("failed", "Role name existed!");
            return "createRole";
        }
        roleRepository.save(role);
        model.addAttribute("success", "Create success");
        return "success";
    }
    @RequestMapping("/redirect")
    public String returnHome(){
        return "redirect:/home";
    }
}
