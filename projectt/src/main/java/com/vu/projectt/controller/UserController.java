package com.vu.projectt.controller;

import com.vu.projectt.model.Role;
import com.vu.projectt.model.User;
import com.vu.projectt.service.RoleService;
import com.vu.projectt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    User user;

    @Autowired
    Role role;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = { "/checkLogin" })
    public String checkLogin(Authentication authentication) {
        String name = authentication.getName();
        user = userService.findByUserName(name);
        role = roleService.findByRoleName(user.getRole());
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String findAll(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("name", "Hello " + user.getFullname());
        return "home";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (!role.getDeleteUser()) {
            model.addAttribute("failed", "You dont have the right to delete user!");
            return "fail";
        }
        userService.deleteById(id);
        return "redirect:/home";
    }

    @RequestMapping("/user/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        if (!role.getUpdateUser()) {
            model.addAttribute("failed", "You dont have the right to edit user!");
            return "fail";
        }
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roleName", roleService.findAllRole());
        return "editForm";
    }

    @RequestMapping("/saveUser")
    public String saveUser(Model model, @ModelAttribute User user, @RequestParam String Passwords, String repassword) {

        if(Passwords.isBlank()){
           userService.save(user);
           return "redirect:/user/detail/" + user.getId();
        }
        if (!Passwords.equals(repassword)) {
            model.addAttribute("failed", Passwords);
            return "editForm";
        }
        
        user.setPassword(passwordEncoder.encode(Passwords));
        userService.save(user);
        return "redirect:/user/detail/" + user.getId();

    }

    @RequestMapping("/user/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("user", userService.getUserById(id));
        return "viewDetail";
    }

    @RequestMapping("/createRole")
    public String createRole(Model model) {
        if (!role.getCreateRoleUser()) {
            model.addAttribute("failed", "You dont have the right to create role!");
            return "fail";
        }
        model.addAttribute("role", new Role());
        return "createRole";
    }

    @RequestMapping("/addRole")
    public String addRole(Model model, @ModelAttribute Role role) {
        if (roleService.findByRoleName(role.getRolename()) != null) {
            model.addAttribute("failed", "Role name existed!");
            return "createRole";
        }
        roleService.add(role);
        model.addAttribute("success", "Create success");
        return "success";
    }

    @RequestMapping("/redirect")
    public String returnHome() {
        return "redirect:/home";
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleName", roleService.findAllRole());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String addUser(Model model, @ModelAttribute User user, @RequestParam String username, String password,
            String repassword) {
        if (!password.equals(repassword)) {
            model.addAttribute("failed", "Confirm password didn't match!");
            model.addAttribute("user", new User());
            model.addAttribute("roleName", roleService.findAllRole());
            return "createUser";
        }
        if (userService.findByUserName(username) != null) {
            model.addAttribute("failed", "Username existed!");
            model.addAttribute("roleName", roleService.findAllRole());
            model.addAttribute("user", new User());
            return "createUser";
        }
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        model.addAttribute("success", "Create Success!");
        return "success";
    }

    @RequestMapping("/roleList")
    public String findAllRole(Model model) {
        model.addAttribute("roleList", roleService.findAllRole());
        return "roleList";
    }
    
    @RequestMapping("/role/edit/{rolename}")
    public String editRole(Model model, @PathVariable String rolename) {
        model.addAttribute("role", roleService.findByRoleName(rolename));
        return "editRoleForm";
    }
    @RequestMapping("/saveRole")
    public String saveRole(Model model, @ModelAttribute Role role){
        roleService.add(role);
        model.addAttribute("success", "Edit success");
        return "success";
    }
}
