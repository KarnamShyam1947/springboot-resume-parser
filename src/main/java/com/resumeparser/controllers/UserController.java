package com.resumeparser.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.resumeparser.dto.ApplicationDTO;
import com.resumeparser.dto.JobDetailsDTO;
import com.resumeparser.dto.UpdateDTO;
import com.resumeparser.entities.UserEntity;
import com.resumeparser.services.ApplicationService;
import com.resumeparser.services.JobService;
import com.resumeparser.services.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final JobService jobService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;

    @Value("${application.resume.parse.url}")
    private String resumeParserServiceUrl;
    
    @GetMapping("/apply/{jobId}")
    public String applyJob(
        @PathVariable("jobId") int jobId,
        Model model
    ) {
        JobDetailsDTO job = jobService.getJobDetails(jobId);

        model.addAttribute("job", job);
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobApplied", applicationService.isUserApplied(jobId));
        model.addAttribute("serviceUrl", resumeParserServiceUrl);
        
        return "user/apply";
    }

    @PostMapping("/apply")
    public String aprocessApplyJob(
        @ModelAttribute("applicationDTO") ApplicationDTO applicationDTO,
        HttpSession session,
        Model model
    ) throws AmazonServiceException, SdkClientException, IOException {
        System.out.println(applicationDTO.getScore());
        applicationService.addApplication(applicationDTO);
        session.setAttribute("applied", "application submitted successfully");

        return "redirect:/user/applications";
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

    @GetMapping("/profile")
    public String updateProfile(Model model) {
        UserEntity currentUser = userService.getCurrentUser();
        UpdateDTO dto = modelMapper.map(currentUser, UpdateDTO.class);
        model.addAttribute("userDTO", dto);
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("role", currentUser.getAuthProvider().toString());
        return "user/profile";
    }
    
    @PostMapping("/profile")
    public String processUpdateProfile(
        @ModelAttribute("userDTO") UpdateDTO updateDTO
    ) throws AmazonServiceException, SdkClientException, IOException {
        userService.updateInfo(updateDTO);
        return "user/dashboard";
    }

}
