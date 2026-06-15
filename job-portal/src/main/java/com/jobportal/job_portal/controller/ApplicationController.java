package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.entity.Application;
import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.security.CustomUserDetails;
import com.jobportal.job_portal.service.ApplicationService;
import com.jobportal.job_portal.service.JobService;
import com.jobportal.job_portal.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/jobs/{id}/apply")
    public String applyForJob(@PathVariable Long id, @RequestParam("coverLetter") String coverLetter,
                              @AuthenticationPrincipal CustomUserDetails userDetails, 
                              jakarta.servlet.http.HttpSession session, Model model) {
        // Generate a 6 digit OTP
        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        
        // Send OTP via SMS (or console fallback)
        System.out.println("=================================================");
        System.out.println("OTP FOR JOB APPLICATION (Job ID: " + id + "): " + otp);
        System.out.println("=================================================");
        
        User user = userDetails.getUser();
        smsService.sendOtpSms(user.getPhone(), otp);
        
        // Store in session
        session.setAttribute("apply_otp", otp);
        session.setAttribute("apply_jobId", id);
        session.setAttribute("apply_coverLetter", coverLetter);
        
        return "redirect:/jobs/" + id + "/apply/verify";
    }

    @org.springframework.web.bind.annotation.GetMapping("/jobs/{id}/apply/verify")
    public String verifyOtpPage(@PathVariable Long id, Model model) {
        model.addAttribute("jobId", id);
        return "verify_otp";
    }

    @PostMapping("/jobs/{id}/apply/verify")
    public String verifyOtpSubmit(@PathVariable Long id, @RequestParam("otp") String submittedOtp,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  jakarta.servlet.http.HttpSession session, Model model) {
                                      
        String sessionOtp = (String) session.getAttribute("apply_otp");
        Long sessionJobId = (Long) session.getAttribute("apply_jobId");
        String coverLetter = (String) session.getAttribute("apply_coverLetter");
        
        if (sessionOtp == null || sessionJobId == null || !sessionJobId.equals(id)) {
            return "redirect:/jobs/" + id + "?error=SessionExpired";
        }
        
        if (!sessionOtp.equals(submittedOtp)) {
            model.addAttribute("error", "Invalid OTP! Check your console logs.");
            model.addAttribute("jobId", id);
            return "verify_otp";
        }
        
        // OTP matches, proceed with application
        User user = userDetails.getUser();
        Job job = jobService.findJobById(id);
        
        try {
            Application application = new Application();
            application.setJob(job);
            application.setUser(user);
            application.setCoverLetter(coverLetter);
            
            applicationService.applyForJob(application);
            
            // Clear session variables
            session.removeAttribute("apply_otp");
            session.removeAttribute("apply_jobId");
            session.removeAttribute("apply_coverLetter");
            
        } catch (RuntimeException e) {
            return "redirect:/jobs/" + id + "?error=" + e.getMessage();
        }
        
        return "redirect:/jobs/" + id + "?success";
    }

    @PostMapping("/employer/applications/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam("status") String status) {
        Application app = applicationService.updateApplicationStatus(id, status);
        if (app != null) {
            return "redirect:/jobs/" + app.getJob().getId() + "/applicants";
        }
        return "redirect:/employer/dashboard";
    }
}
