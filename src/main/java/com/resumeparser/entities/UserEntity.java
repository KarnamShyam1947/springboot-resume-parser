package com.resumeparser.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resumeparser.enums.AuthProvider;
import com.resumeparser.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "hr", cascade = CascadeType.ALL)
    private CompanyEntity company;
    
    private String name;
    private String email;
    private String twitter;
    private String linkedin;
    private String facebook;
    private String instagram;
    private String websiteUrl;
    private String profileUrl;
    private boolean isVerified;
    private String uniqueToken;
    private String authProviderId;
    private LocalDateTime experation;
}
