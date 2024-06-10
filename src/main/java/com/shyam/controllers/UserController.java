package com.shyam.controllers;

import java.io.IOException;
import java.util.List;

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
import com.shyam.dto.MyApplications;
import com.shyam.services.ApplicationService;
import com.shyam.services.JobService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final ApplicationService applicationService;
    private final JobService jobService;

    @GetMapping("/apply/{jobId}")
    public String applyJob(
        @PathVariable("jobId") int jobId,
        Model model
    ) {
        JobDetailsDTO job = jobService.getJob(jobId);

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

    @GetMapping("/applications")
    public String application(Model model) {
        List<MyApplications> userApplications = applicationService.getUserApplications();
        model.addAttribute("userApplications", userApplications);
        return "user/applications";
    }
    
}
