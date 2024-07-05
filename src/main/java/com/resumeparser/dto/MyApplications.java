package com.resumeparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyApplications {
    private int applicationId;
    private double score;
    private String resumeUrl;
    private int jobId;
    private String jobTitle;
    private String jobExperience;
    private String jobSalary;
}
