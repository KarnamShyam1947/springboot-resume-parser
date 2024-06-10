package com.shyam.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyam.config.custom.MyUserDetails;
import com.shyam.dto.JobDetailsDTO;
import com.shyam.entities.JobEntity;
import com.shyam.services.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final JobService jobService;

    @GetMapping("/")
    public String idnex(Model model){
        List<JobEntity> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);

        return "index";
    }
    
    @ResponseBody
    @GetMapping("/about")
    public Object about(){
        MyUserDetails user = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @GetMapping("/job-details/{jobId}")
    public String getJob(
        @PathVariable("jobId") int jobId,
        Model model
    ) {
        JobDetailsDTO job = jobService.getJob(jobId);
        model.addAttribute("job", job);
        
        return "job/job-details";
    }

}
