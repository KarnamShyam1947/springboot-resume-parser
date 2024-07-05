package com.resumeparser.config.security.oauth.user;

import java.util.Map;

import com.resumeparser.enums.AuthProvider;

public class GoogleOAuthUser extends BaseOAuthUser {
    public GoogleOAuthUser(Map<String, Object> attributes) {
        super(attributes);
    }  

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getAuthProvider() {
        return AuthProvider.GOOGLE.name();
    }
}
