package com.resumeparser.config.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.resumeparser.config.security.oauth.user.BaseOAuthUser;
import com.resumeparser.config.security.oauth.user.OAuth2UserInfoFactory;
import com.resumeparser.entities.UserEntity;
import com.resumeparser.enums.AuthProvider;
import com.resumeparser.enums.Role;
import com.resumeparser.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuthUser = super.loadUser(userRequest);

        BaseOAuthUser oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            userRequest.getClientRegistration().getRegistrationId(), 
            oAuthUser.getAttributes()
        );

        UserEntity user = saveOrUpdateOAuthUser(oAuth2UserInfo);
                
        return new CustomOAuthUser(user, oAuthUser.getAttributes());
    }

    public UserEntity saveOrUpdateOAuthUser(BaseOAuthUser oAuth2UserInfo) {
        UserEntity user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        if (user == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setAuthProvider(AuthProvider.valueOf(oAuth2UserInfo.getAuthProvider()));
            userEntity.setProfileUrl(oAuth2UserInfo.getImageUrl());
            userEntity.setAuthProviderId(oAuth2UserInfo.getId());
            userEntity.setEmail(oAuth2UserInfo.getEmail());
            userEntity.setName(oAuth2UserInfo.getName());
            userEntity.setRole(Role.USER);
            return userRepository.save(userEntity);
        }
        else {
            UserEntity userEntity2 = userRepository.findByEmail(user.getEmail());
            userEntity2.setProfileUrl(oAuth2UserInfo.getImageUrl());
            userEntity2.setName(oAuth2UserInfo.getName());
            return userRepository.save(userEntity2);
        }

    }
    
}
