package com.shyam.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.shyam.dto.ApplicationDTO;
import com.shyam.dto.UserApplicationDTO;
import com.shyam.entities.ApplicationEntity;
import com.shyam.repositories.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final S3Service s3Service;
    private final ModelMapper mapper;

    public ApplicationEntity addApplication(
        ApplicationDTO applicationDTO
    ) throws AmazonServiceException, SdkClientException, IOException {

        if (applicationDTO.getFile() != null && !applicationDTO.getFile().isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String profileUrl = s3Service.uploadFile(applicationDTO.getFile(), uuid);

            int id = userService.getCurrentUser().getId();
            
            ApplicationEntity application = mapper.map(applicationDTO, ApplicationEntity.class);
            application.setResumeUrl(profileUrl);
            application.setUserId(id);
            application.setUuid(uuid);
            application.setId(0);

            return applicationRepository.save(application);
        }
        return null;
    }

    public boolean isUserApplied(int jodId) {
        int id = userService.getCurrentUser().getId();
        List<ApplicationEntity> userApplied = applicationRepository.isUserApplied(jodId, id);
        return userApplied.size() > 0;
    }

    public List<ApplicationEntity> getApplications(int jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    public List<UserApplicationDTO> getCurrentUserApplications() {
        return applicationRepository.getUserApplications(userService.getCurrentUser().getId());
    }

}
