package com.shyam.config.custom;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shyam.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().name());
        return List.of(authority);        
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();        
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();        
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isVerified();
    }

    public int getId() {
        return userEntity.getId();
    }

}
