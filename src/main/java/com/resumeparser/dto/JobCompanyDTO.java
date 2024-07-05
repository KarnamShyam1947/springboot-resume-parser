package com.resumeparser.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCompanyDTO {
    private int jobId;
    private String jobTitle;
    private String salary;
    private String jobType;
    private String experience;
    private LocalDate endDate;
    private int avaliblePosts;
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyLogo;
}
