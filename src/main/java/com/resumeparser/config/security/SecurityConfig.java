package com.resumeparser.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.resumeparser.config.custom.MyUserDetailsService;
import com.resumeparser.config.security.oauth.CustomOAuthUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
// @EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final CustomOAuthUserService customOAuthUserService;

    private String[] WHITELIST_URLS = {
        "auth/**",
        "css/**",
        "fonts/**",
        "images/**",
        "js/**",
        "webfonts/**",
        "test",
        "contact"
    };

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }

    @Bean
    AuthenticationManager getAuthManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.csrf(csrf -> csrf.disable());

        security.authorizeHttpRequests(
            authorizer -> authorizer
                            .requestMatchers(WHITELIST_URLS).permitAll()
                            .requestMatchers("/company/**").hasAuthority("HR")
                            .requestMatchers("/jobs/**").hasAuthority("HR")
                            .requestMatchers("/user/**").hasAuthority("USER")
                            .anyRequest().authenticated()
        );

        security.formLogin(
            login -> login
                        .loginPage("/auth/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/process-login")
                        .defaultSuccessUrl("/")
                        .permitAll()
        );

        security.rememberMe(
            remember -> remember
                            .rememberMeParameter("remember-me")
                            .tokenValiditySeconds(60 * 60 * 3) // 3 days
                            .key("this-is-seceret")
        );

        security.oauth2Login(oauth -> oauth
                                        .loginPage("/auth/login")
                                        .userInfoEndpoint(
                                            user -> user.userService(customOAuthUserService)
                                        )
                                        .permitAll()
        );

        return security.build();
    }
    
}

