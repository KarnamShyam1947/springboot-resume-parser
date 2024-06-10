package com.shyam.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.shyam.config.custom.MyUserDetails;
import com.shyam.dto.ApplicationDTO;
import com.shyam.dto.MyApplications;
import com.shyam.dto.UserApplicationDTO;
import com.shyam.entities.ApplicationEntity;
import com.shyam.repositories.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final S3Service s3Service;
    private final ModelMapper mapper;

    public void addApplication(ApplicationDTO applicationDTO) throws AmazonServiceException, SdkClientException, IOException {
        if (applicationDTO.getFile() != null && !applicationDTO.getFile().isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String profileUrl = s3Service.uploadFile(applicationDTO.getFile(), uuid);

            int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            ApplicationEntity application = mapper.map(applicationDTO, ApplicationEntity.class);
            application.setResumeUrl(profileUrl);
            application.setUserId(id);
            application.setUuid(uuid);
            application.setId(id);

            applicationRepository.save(application);
        }
    }

    public boolean isUserApplied(int jodId) {
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<ApplicationEntity> userApplied = applicationRepository.isUserApplied(jodId, id);
        return userApplied.size() > 0;
    }

    public List<UserApplicationDTO> getApplications(int jobId) {
        return applicationRepository.getApplications(jobId);
    }

    public List<MyApplications> getUserApplications() {
        int id = ((MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return applicationRepository.getUserApplications(id);
    }

}
