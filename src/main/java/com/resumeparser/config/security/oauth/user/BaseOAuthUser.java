package com.resumeparser.config.security.oauth.user;

import java.util.Map;

import lombok.Data;

@Data
abstract public class BaseOAuthUser {
    protected Map<String, Object> attributes;

    public BaseOAuthUser(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract String getAuthProvider();
}
