package com.resumeparser.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;

@Service
@PropertySource("classpath:aws.properties")
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public String uploadFile(MultipartFile file, String key) throws AmazonServiceException, SdkClientException, IOException {
        key = key+".pdf";
        amazonS3.putObject(bucketName, key, file.getInputStream(), null);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
    }

    public void deleteResume(String key) {
        amazonS3.deleteObject(bucketName, key+".pdf");
    }

}
