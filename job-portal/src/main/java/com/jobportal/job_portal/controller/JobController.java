package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.security.CustomUserDetails;
import com.jobportal.job_portal.service.ApplicationService;
import com.jobportal.job_portal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public String browseJobs(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Job> jobs;
        if (keyword != null && !keyword.isEmpty()) {
            jobs = jobService.searchJobs(keyword);
        } else {
            jobs = jobService.findAllJobs();
        }
        model.addAttribute("jobs", jobs);
        return "jobs";
    }
    
    @GetMapping("/{id}")
    public String jobDetails(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);
        if (userDetails != null && userDetails.getUser().getRole().name().equals("STUDENT")) {
            boolean applied = applicationService.hasUserApplied(userDetails.getUser(), job);
            model.addAttribute("hasApplied", applied);
        }
        return "job_details";
    }

    @GetMapping("/create")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "employer/create_job";
    }

    @PostMapping("/create")
    public String createJob(@ModelAttribute("job") Job job, @AuthenticationPrincipal CustomUserDetails userDetails) {
        job.setEmployer(userDetails.getUser());
        jobService.saveJob(job);
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/{id}/applicants")
    public String viewApplicants(@PathVariable Long id, Model model) {
        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);
        model.addAttribute("applications", applicationService.getApplicationsByJob(job));
        return "employer/applicants";
    }
}
