package com.jobportal.job_portal.service;

import com.jobportal.job_portal.entity.Application;
import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application applyForJob(Application application) {
        // Prevent duplicate applications
        if (hasUserApplied(application.getUser(), application.getJob())) {
            throw new RuntimeException("You have already applied for this job.");
        }
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }

    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public Application updateApplicationStatus(Long id, String status) {
        Application app = getApplicationById(id);
        if (app != null) {
            app.setStatus(status);
            return applicationRepository.save(app);
        }
        return null;
    }

    public boolean hasUserApplied(User user, Job job) {
        return applicationRepository.findByUserAndJob(user, job).isPresent();
    }
}
