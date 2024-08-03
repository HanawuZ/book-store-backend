package com.backend.app.security.oauth2.utils;

import java.util.HashMap;
import java.util.Map;

public class GitHubPrincipalExtractor {

    private GitHubPrincipalExtractor() {}

    public static Map<String, Object> extract(Map<String, Object> userAttributes) {

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", userAttributes.get("login"));   
        userMap.put("providerId", userAttributes.get("id"));
        userMap.put("provider", "GitHub");
        userMap.put("profilePicture", userAttributes.get("avatar_url"));
        userMap.put("fullname", userAttributes.get("name"));
        userMap.put("email", userAttributes.get("email"));
        return userMap;
    }

}
