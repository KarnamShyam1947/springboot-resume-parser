package com.shyam.dto;

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
    private String name;
    private String email;
}
