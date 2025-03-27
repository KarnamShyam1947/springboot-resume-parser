package com.resumeparser.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDTO {

    private String email;
    private String address;
    private String twitter;
    private String linkedin;
    private String facebook;
    private String instagram;
    private String websiteUrl;
    private String description;
    private MultipartFile logo;
    private MultipartFile banner;
    private MultipartFile profile;
    
}
