package com.resumeparser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resumeparser.dto.JobDTO;
import com.resumeparser.services.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/add")
    public String add(@ModelAttribute("jobDTO") JobDTO jobDTO) {
        return "job/add";
    }

    @PostMapping("/add")
    public String processAdd(
        @Valid @ModelAttribute("jobDTO") JobDTO jobDTO,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "job/add";
        }

        jobService.addJob(jobDTO);
        return "redirect:/company/jobs-list";
    }

}
