package com.resumeparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@PropertySource("classpath:aws.properties")
public class AWSS3Config {

    @Value("${aws.secret.access.key}")
    private String awsSecretAccessKey;
    
    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;
    
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
