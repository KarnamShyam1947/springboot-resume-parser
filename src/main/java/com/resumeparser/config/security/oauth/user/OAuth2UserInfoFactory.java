package com.resumeparser.config.security.oauth.user;

import java.util.Map;

import com.resumeparser.enums.AuthProvider;

public class OAuth2UserInfoFactory {
    public static BaseOAuthUser getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuthUser(attributes);
            
        } 
        else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
            return new GitHubOAuthUser(attributes);
        } 
        else {
            throw new RuntimeException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
