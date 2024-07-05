package com.resumeparser.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationDTO {
    private int jobId;
    private double score;
    private MultipartFile file;
}
