package com.shyam.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyam.dto.JobDTO;
import com.shyam.dto.UserApplicationDTO;
import com.shyam.entities.JobEntity;
import com.shyam.services.ApplicationService;
import com.shyam.services.JobService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final ApplicationService applicationService;
    private final JobService jobService;

    @GetMapping("/")
    public String getJob(Model model) {
        List<JobEntity> jobs = jobService.getHrJobs();
        model.addAttribute("jobs", jobs);
        return "job/all";
    }

    @GetMapping("/add-job")
    public String addJob(@ModelAttribute("jobDTO") JobDTO jobDTO) {
        return "job/add";
    }
    
    @PostMapping("/add-job")
    public String processAddJob(
        @Valid @ModelAttribute("jobDTO") JobDTO jobDTO,
        BindingResult result,
        HttpSession session
    ) {
        if (result.hasErrors())     
            return "job/add";
        
        jobService.addJob(jobDTO);
        session.setAttribute("jobAdded", "New job post added successfully");

        return "redirect:/job/";
    }

    @GetMapping("/application/{jobId}")
    public String applications(
        @PathVariable("jobId") int jobId,
        Model model
    ) {
        List<UserApplicationDTO> applications = applicationService.getApplications(jobId);
        model.addAttribute("applications", applications);
        
        return "job/applications";
    }
    
    @GetMapping("/delete/{jobId}")
    public String deleteJob(
        @PathVariable("jobId") int jobId,
        HttpSession session
    ) {
        jobService.deleteJob(jobId);
        session.setAttribute("jobDeleted", "job post deleted successfully");
        return "redirect:/job/";
    }
}
