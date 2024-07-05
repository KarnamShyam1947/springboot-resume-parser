package com.resumeparser.config.security.oauth;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.resumeparser.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuthUser implements OAuth2User {

    private final UserEntity oAuth2User;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(oAuth2User.getRole().name());
        return List.of(authority);
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public UserEntity getUserEntity() {
        return oAuth2User;
    }
    
}
