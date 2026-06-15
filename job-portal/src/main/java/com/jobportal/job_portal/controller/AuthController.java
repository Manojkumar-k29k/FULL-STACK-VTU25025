package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.entity.Role;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("roleType") String roleType, Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email is already registered!");
            return "register";
        }
        
        if ("STUDENT".equalsIgnoreCase(roleType)) {
            user.setRole(Role.STUDENT);
        } else if ("EMPLOYER".equalsIgnoreCase(roleType)) {
            user.setRole(Role.EMPLOYER);
        } else {
            user.setRole(Role.ADMIN);
        }

        userService.saveUser(user);
        return "redirect:/login?success";
    }
}
