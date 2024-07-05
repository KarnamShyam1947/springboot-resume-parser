package com.resumeparser.services;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.resumeparser.dto.HRDTO;
import com.resumeparser.dto.JobApplicationDTO;
import com.resumeparser.dto.UpdateDTO;
import com.resumeparser.entities.CompanyEntity;
import com.resumeparser.entities.UserEntity;
import com.resumeparser.repositories.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final S3Service s3Service;

    @Value("${application.default.company.logo}")
    private String defaultLogoUrl;

    @Value("${application.default.company.banner}")
    private String defaultBannerUrl;

    public CompanyEntity saveCompany(HRDTO hrdto) {
        CompanyEntity company = modelMapper.map(hrdto, CompanyEntity.class);
        company.setName(hrdto.getCompanyName());
        company.setBannerUrl(defaultBannerUrl);
        company.setLogoUrl(defaultLogoUrl);

        UserEntity hr = userService.registerHR(hrdto, company);
        company.setHr(hr);

        return companyRepository.save(company);
    }
    
    public CompanyEntity saveCompany(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    public CompanyEntity getCurrentCompany() {
        UserEntity currentUser = userService.getCurrentUser();
        return currentUser.getCompany();
    }

    public CompanyEntity updateCompanyProfile(
        UpdateDTO updateDTO
    ) throws AmazonServiceException, SdkClientException, IOException {
        
        CompanyEntity currentCompany = getCurrentCompany();

        if (updateDTO.getLogo() != null && !updateDTO.getLogo().isEmpty()) {
            String logoUrl = s3Service.uploadFile(updateDTO.getLogo(), "logos/"+currentCompany.getName());
            currentCompany.setLogoUrl(logoUrl);
        }

        if (updateDTO.getBanner() != null && !updateDTO.getBanner().isEmpty()) {
            String bannerUrl = s3Service.uploadFile(updateDTO.getBanner(), "banners/"+currentCompany.getName());
            currentCompany.setBannerUrl(bannerUrl);
        }

        return saveCompany(currentCompany);
    }
    
    public UserEntity getHr(int companyId) {
        return companyRepository.findById(companyId).getHr();
    }

    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<JobApplicationDTO> getApplications(int jobId){
        return companyRepository.getApplications(jobId);
    }
}
