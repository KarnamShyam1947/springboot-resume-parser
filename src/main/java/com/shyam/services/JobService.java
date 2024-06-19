package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.JobCompanyDTO;
import com.shyam.dto.JobDTO;
import com.shyam.dto.JobDetailsDTO;
import com.shyam.entities.JobEntity;
import com.shyam.repositories.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {
    
    private final CompanyService companyService;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    public JobEntity addJob(JobDTO jobDTO) {
        JobEntity job = modelMapper.map(jobDTO, JobEntity.class);
        job.setId(0);
        job.setCompanyId(companyService.getCurrentCompany().getId());

        return jobRepository.save(job);
    }

    public List<JobEntity> getCurrentCompanyJobs() {
        return jobRepository.findByCompanyId(companyService.getCurrentCompany().getId());
    }

    public List<JobEntity> getCompanyJobDetails(int companyId) {
        return jobRepository.findByCompanyId(companyId);
    }

    public JobDetailsDTO getJobDetails(int jobId) {
        return jobRepository.getJobDetails(jobId);
    }

    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }
    
    public List<JobCompanyDTO> getAllCompanyJobs() {
        return jobRepository.getCompanyJobDetails();
    }

}
