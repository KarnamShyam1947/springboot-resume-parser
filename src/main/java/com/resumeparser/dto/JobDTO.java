package com.resumeparser.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.resumeparser.validators.FutureDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobDTO {
    @NotBlank(message = "salary is required")
    private String salary;

    @NotBlank(message = "title is required")
    private String jobTitle;

    @NotBlank(message = "experience is required")
    private String experience;
    
    @Min(value = 1, message = "available post must grater than 0")
    private int avaliblePosts;

    @NotBlank(message = "jd is required")
    private String jobDiscription;

    @NotBlank(message = "Please select job type")
    private String jobType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureDate(message = "date must be future")
    private LocalDate endDate;

    @AssertTrue(message = "please agree terms and conditions")
    private boolean tac;
}
