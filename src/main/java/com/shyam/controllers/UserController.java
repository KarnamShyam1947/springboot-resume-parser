package com.shyam.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.shyam.dto.ApplicationDTO;
import com.shyam.dto.JobDetailsDTO;
import com.shyam.services.ApplicationService;
import com.shyam.services.JobService;
import com.shyam.services.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final JobService jobService;
    private final UserService userService;
    private final ApplicationService applicationService;
    
    @GetMapping("/apply/{jobId}")
    public String applyJob(
        @PathVariable("jobId") int jobId,
        Model model
    ) {
        JobDetailsDTO job = jobService.getJobDetails(jobId);

        model.addAttribute("job", job);
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobApplied", applicationService.isUserApplied(jobId));
        
        return "user/apply";
    }

    @PostMapping("/apply")
    public String aprocessApplyJob(
        @ModelAttribute("applicationDTO") ApplicationDTO applicationDTO,
        HttpSession session
    ) throws AmazonServiceException, SdkClientException, IOException {
        System.out.println(applicationDTO.getScore());
        applicationService.addApplication(applicationDTO);
        session.setAttribute("applied", "application submitted successfully");

        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String renderDashboard(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "user/dashboard";
    }
    
    @GetMapping("/applications")
    public String renderApplications(Model model) {
        model.addAttribute("jobs", applicationService.getCurrentUserApplications());
        return "user/applications";
    }

}
