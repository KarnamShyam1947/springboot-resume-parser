package com.resumeparser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resumeparser.services.CompanyService;
import com.resumeparser.services.JobService;
import com.resumeparser.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final CompanyService companyService;
    private final UserService userService;
    private final JobService jobService;

    @GetMapping("/")
    public String idnex(Model model){
        model.addAttribute("jobs", jobService.getAllJobs());
        model.addAttribute("companies", companyService.getAllCompanies());
    
        return "index";
    }
    
    @GetMapping("/companies")
    public String companies(Model model){
        model.addAttribute("companies", companyService.getAllCompanies());
    
        return "pages/companies";
    }
    
    @GetMapping("/jobs-list")
    public String jobs(
        @RequestParam(name = "type", defaultValue = "0") String type, 
        Model model
    ) {
        model.addAttribute("jobs", jobService.getAllCompanyJobs());

        if (type.equals("0"))    
            return "pages/jobs";

        else
            return "pages/jobs1"; 
    }
    
    @GetMapping("/jobs1")
    public String jobs1(Model model){
        model.addAttribute("jobs", jobService.getAllCompanyJobs());
    
        return "pages/jobs1";
    }
    
    @GetMapping("/test")
    public String test(Model model){
        return "test";
    }
    
    @GetMapping("/job-details/{jobId}")
    public String jobDetails(
        @PathVariable("jobId") int jobId,
        Model model 
    ){
        model.addAttribute("job", jobService.getJobDetails(jobId));
        return "pages/job-details";
    }

    @GetMapping("/company-details/{companyId}")
    public String renderDetails(
        @PathVariable("companyId") int companyId,
        Model model
    ) {
        model.addAttribute("user", companyService.getHr(companyId));
        model.addAttribute("jobs", jobService.getCompanyJobDetails(companyId));
        return "pages/company-details";
    }
    
    @ResponseBody
    @GetMapping("/about")
    public Object about(){
        return userService.getCurrentUser().getId();
    }
    
    @GetMapping("/contact")
    public Object contact(){
        return "pages/contact";
    }
}
