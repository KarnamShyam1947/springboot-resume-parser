package com.shyam.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyam.enums.AuthProvider;
import com.shyam.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    private String password;

    @Lob
    private String companyAddress;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;
    
    private String name;
    private String email;
    private String profileUrl;
    private boolean isVerified;
    private String uniqueToken;
    private String authProviderId;
    private LocalDateTime experation;

    private String companyName;                               
    private String companyWebsiteUrl;
}
