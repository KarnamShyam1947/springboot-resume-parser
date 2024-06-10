package com.shyam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobDTO {
    @NotBlank(message = "salary is requried")
    private String salary;

    @NotBlank(message = "title is requried")
    private String jobTitle;

    @NotBlank(message = "expernice is requried")
    private String experince;
    
    @Min(value = 0, message = "avalible post must grater than 0")
    private int avaliblePosts;

    @NotBlank(message = "jd is requried")
    private String jobDiscription;
}
