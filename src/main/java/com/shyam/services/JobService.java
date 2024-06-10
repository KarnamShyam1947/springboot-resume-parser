package com.shyam.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shyam.config.custom.MyUserDetails;
import com.shyam.dto.JobDTO;
import com.shyam.dto.JobDetailsDTO;
import com.shyam.entities.JobEntity;
import com.shyam.repositories.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final ModelMapper mapper;
    private final JobRepository jobRepository;

    public JobEntity addJob(JobDTO jobDTO) {
        JobEntity job = mapper.map(jobDTO, JobEntity.class);
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        job.setId(0);
        job.setHrId(id);
        
        return jobRepository.save(job);
    }

    public List<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }

    public JobDetailsDTO getJob(int jobId) {
        return jobRepository.getJobDetails(jobId);
    }

    public List<JobEntity> getHrJobs() {
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return jobRepository.findByHrId(id);
    }

    public void deleteJob(int jobId) {
        Optional<JobEntity> job = jobRepository.findById(jobId);
        if(job.isPresent())
            jobRepository.delete(job.get());
    }
}
