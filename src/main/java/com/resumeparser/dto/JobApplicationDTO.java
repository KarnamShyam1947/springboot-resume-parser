package com.resumeparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private int userId;
    private String userName;
    private String userProfile;
    private int applicationId;
    private double score;
    private String resumeUrl;
}
