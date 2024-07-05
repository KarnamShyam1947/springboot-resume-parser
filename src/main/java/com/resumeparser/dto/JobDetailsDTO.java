package com.resumeparser.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailsDTO {
    private int jobId;
    private String jobTitle;
    private String salary;
    private String jobDiscription;
    private String jobType;
    private LocalDate endDate;
    private String experince;
    private int avaliblePosts;
    private int companyId;
    private String companyName;
    private String logoUrl;
    private String companyAddress;
    // j.jobTitle, j.salary, j.jobDiscription, j.jobType, j.endDate, j.experience, j.avaliblePosts, c.id, c.name, c.logoUrl, c.address
}
