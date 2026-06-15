package com.jobportal.job_portal.config;

import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.Role;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.repository.UserRepository;
import com.jobportal.job_portal.service.JobService;
import com.jobportal.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Only run if the database is empty — uses COUNT(*) instead of SELECT * for efficiency
        if (userRepository.count() == 0) {
            
            // 1. Create a mock employer
            User employer = new User();
            employer.setFullName("Tech Innovations Inc.");
            employer.setEmail("hr@techinnovations.com");
            employer.setPassword("password123");
            employer.setRole(Role.EMPLOYER);
            employer.setCompanyName("Tech Innovations Inc.");
            employer.setActive(true);
            
            employer = userService.saveUser(employer);
            
            // 2. Add some mock jobs for this employer
            Job job1 = new Job();
            job1.setTitle("Senior Full-Stack Developer");
            job1.setCategory("Information Technology");
            job1.setEmployer(employer);
            job1.setDescription("We are looking for a Senior Full-Stack Developer skilled in Spring Boot and React to join our fast-growing startup. You will be responsible for leading our core architecture and building scalable products.");
            job1.setExperienceRequired("5+ Years");
            job1.setLocation("New York, NY (Hybrid)");
            job1.setRequiredSkills("Java, Spring Boot, React, MySQL, AWS");
            job1.setSalary("$120,000 - $150,000");
            job1.setPostedDate(LocalDate.now().minusDays(2));
            job1.setLastDate(LocalDate.now().plusDays(28));
            job1.setStatus("Active");
            jobService.saveJob(job1);
            
            Job job2 = new Job();
            job2.setTitle("Junior Software Engineer");
            job2.setCategory("Information Technology");
            job2.setEmployer(employer);
            job2.setDescription("A great opportunity for a junior developer to learn from industry experts. You will work on internal tooling and shadow our senior engineers on main projects.");
            job2.setExperienceRequired("0-2 Years");
            job2.setLocation("San Francisco, CA (Remote)");
            job2.setRequiredSkills("Java, JavaScript, HTML/CSS");
            job2.setSalary("$75,000 - $90,000");
            job2.setPostedDate(LocalDate.now().minusDays(5));
            job2.setLastDate(LocalDate.now().plusDays(15));
            job2.setStatus("Active");
            jobService.saveJob(job2);
            
            Job job3 = new Job();
            job3.setTitle("Data Analyst");
            job3.setCategory("Data Science");
            job3.setEmployer(employer);
            job3.setDescription("We need a data enthusiast to help turn numbers into actionable insights. Must have experience with SQL and data visualization tools.");
            job3.setExperienceRequired("2+ Years");
            job3.setLocation("Austin, TX");
            job3.setRequiredSkills("Python, SQL, Tableau, Pandas");
            job3.setSalary("$85,000 - $110,000");
            job3.setPostedDate(LocalDate.now());
            job3.setLastDate(LocalDate.now().plusDays(30));
            job3.setStatus("Active");
            jobService.saveJob(job3);
            
            Job job4 = new Job();
            job4.setTitle("Artificial Intelligence Engineer");
            job4.setCategory("Data Science");
            job4.setEmployer(employer);
            job4.setDescription("Looking for an AI engineer to develop cutting-edge machine learning models. Experience with LLMs and prompt engineering is highly desired.");
            job4.setExperienceRequired("3+ Years");
            job4.setLocation("Remote");
            job4.setRequiredSkills("Python, PyTorch, TensorFlow, LLMs");
            job4.setSalary("$140,000 - $180,000");
            job4.setPostedDate(LocalDate.now().minusDays(1));
            job4.setLastDate(LocalDate.now().plusDays(45));
            job4.setStatus("Active");
            jobService.saveJob(job4);

            Job job5 = new Job();
            job5.setTitle("Senior Product Manager");
            job5.setCategory("Management");
            job5.setEmployer(employer);
            job5.setDescription("Lead the product vision and work closely with engineering and design to deliver an outstanding user experience. You will define the roadmap and prioritize features.");
            job5.setExperienceRequired("5+ Years");
            job5.setLocation("Chicago, IL (Hybrid)");
            job5.setRequiredSkills("Agile, Product Strategy, Jira, User Research");
            job5.setSalary("$130,000 - $160,000");
            job5.setPostedDate(LocalDate.now());
            job5.setLastDate(LocalDate.now().plusDays(20));
            job5.setStatus("Active");
            jobService.saveJob(job5);
            
            System.out.println("✅ Database has been pre-populated with mock data.");
        }
    }
}
