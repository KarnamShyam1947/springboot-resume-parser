package com.shyam.services;

import java.io.IOException;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.shyam.config.custom.MyUserDetails;
import com.shyam.config.security.oauth.CustomOAuthUser;
import com.shyam.dto.HRDTO;
import com.shyam.dto.LoginDTO;
import com.shyam.dto.UpdateDTO;
import com.shyam.dto.UserDTO;
import com.shyam.entities.CompanyEntity;
import com.shyam.entities.UserEntity;
import com.shyam.enums.AuthProvider;
import com.shyam.enums.Role;
import com.shyam.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper;
    private final HttpSession session;
    // private final CompanyService companyService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${application.default.profile.url}")
    private String defaultProfileUrl;

    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public UserEntity getByToken(String token) {
        return userRepository.findByUniqueToken(token);
    }

    public UserEntity registerHR(HRDTO userDTO, CompanyEntity company) {
        System.out.println("\nI am here\n");
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userEntity.setId(0);
        userEntity.setCompany(company);
        userEntity.setAuthProvider(AuthProvider.LOCAL);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getProfilePhoto() != null && !userDTO.getProfilePhoto().isEmpty()) {
            // call upload service get public url
            userEntity.setProfileUrl(null);
        }
        else                                                                                                
            userEntity.setProfileUrl(defaultProfileUrl);
        

        return saveUser(userEntity, Role.HR);
    }

    public UserEntity registerUser(UserDTO userDTO) {
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userEntity.setId(0);
        userEntity.setAuthProvider(AuthProvider.LOCAL);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getProfilePhoto() != null && !userDTO.getProfilePhoto().isEmpty()) {
            // call upload service get public url
            userEntity.setProfileUrl(null);
        }
        else 
            userEntity.setProfileUrl(defaultProfileUrl);
        

        return saveUser(userEntity, Role.USER);
    }

    public UserEntity saveUser(UserEntity userEntity, Role role) {
        userEntity.setRole(role);
        
        return userRepository.save(userEntity);
    }

    public UserEntity login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
            )
        );

        return userRepository.findByEmail(loginDTO.getEmail());
    }

    public UserEntity validateToken(String token) {
        UserEntity user = userRepository.findByUniqueToken(token);

        if (user == null) {
            session.setAttribute("token", "Invalid user validation token");
            return null;
        }

        if(user.getExperation().isBefore(LocalDateTime.now())) {
            session.setAttribute("token", "User validation token expired");
            return null;
        }

        return user;            
    }

    public int activateAccount(String token) {
        UserEntity user = validateToken(token);

        if (user == null)
            return 1;
        

        user.setVerified(true);
        user.setExperation(null);
        user.setUniqueToken(null);

        userRepository.save(user);

        return 0;
    }

    public void setPassword(UserEntity user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public UserEntity getCurrentUser() {
        UserEntity currentUser = null;

        try {
            MyUserDetails user = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            currentUser = user.getUserEntity();
        } catch (Exception e) {
            CustomOAuthUser user = (CustomOAuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            currentUser = user.getUserEntity();
        }

        return currentUser;
    }

    public UserEntity updateInfo(
        UpdateDTO updateDTO
    ) throws AmazonServiceException, SdkClientException, IOException {
        
        UserEntity currentUser = getCurrentUser();

        currentUser.setTwitter(updateDTO.getTwitter());
        currentUser.setLinkedin(updateDTO.getLinkedin());
        currentUser.setFacebook(updateDTO.getFacebook());
        currentUser.setInstagram(updateDTO.getInstagram());
        currentUser.setWebsiteUrl(updateDTO.getWebsiteUrl());
        currentUser.setDescription(updateDTO.getDescription());

        // if (currentUser.getRole() == Role.HR) {
        //     companyService.updateCompanyProfile(updateDTO);
        // }

        return userRepository.save(currentUser);
    }

}
