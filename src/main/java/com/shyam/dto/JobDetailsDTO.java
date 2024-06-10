package com.shyam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailsDTO {
    private int avaliblePosts;
    private String experince;
    private String jobTitle;
    private String jobDiscription;
    private String companyAddress;
    private String companyName;
    private String companyWebsiteUurl;
    private String hrEmail;
    private String hrName;
}
