package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.security.CustomUserDetails;
import com.jobportal.job_portal.service.ApplicationService;
import com.jobportal.job_portal.service.JobService;
import com.jobportal.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/student/dashboard")
    public String studentDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("applications", applicationService.getApplicationsByUser(user));
        return "student/dashboard";
    }

    @GetMapping("/employer/dashboard")
    public String employerDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("jobs", jobService.findJobsByEmployer(user));
        return "employer/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        return "admin/dashboard";
    }
}
