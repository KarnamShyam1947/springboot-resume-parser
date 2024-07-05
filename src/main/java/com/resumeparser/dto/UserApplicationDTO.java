package com.resumeparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApplicationDTO {
    private int applicationId;
    private double score;
    private String resumeUrl;
    private int jobId;
    private String jobTitle;
    private String salary;
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyLogoUrl;
}
