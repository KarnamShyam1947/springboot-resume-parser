package com.resumeparser.controllers;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.resumeparser.dto.UpdateDTO;
import com.resumeparser.services.CompanyService;
import com.resumeparser.services.JobService;
import com.resumeparser.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JobService jobService;
    
    @GetMapping("/dashboard")
    public String renderDashboard() {
        return "company/dashboard";
    }
    
    @GetMapping("/update")
    public String renderUpdate(Model model) {
        UpdateDTO updateDTO = modelMapper.map(userService.getCurrentUser(), UpdateDTO.class);
        model.addAttribute("updateDTO", updateDTO);
        
        return "company/update";
    }
    
    @PostMapping("/update")
    public String processUpdate(
        @ModelAttribute("updateDTO") UpdateDTO updateDTO
    ) throws AmazonServiceException, SdkClientException, IOException {

        userService.updateInfo(updateDTO);
        companyService.updateCompanyProfile(updateDTO);

        return "redirect:/company/dashboard";

    }

    @GetMapping("/details")
    public String renderDetails(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("jobs", jobService.getCurrentCompanyJobs());
        return "company/details";
    }

    @GetMapping("/jobs-list")
    public String renderJobsList(Model model) {
        model.addAttribute("jobs", jobService.getCurrentCompanyJobs());
        return "company/jobs-list";
    }
    
    @GetMapping("/job-details/{jobId}")
    public String renderJobDetails(@PathVariable("jobId") int jobId, Model model) {
        model.addAttribute("jobs", companyService.getApplications(jobId));
        return "company/jobs-details";
    }

}
