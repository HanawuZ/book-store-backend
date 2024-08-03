package com.backend.app.security.oauth2.utils;

import java.util.HashMap;
import java.util.Map;

public class GooglePrincipalExtractor {

    private GooglePrincipalExtractor() {}

    public static Map<String, Object> extract(Map<String, Object> userAttributes) {

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", userAttributes.get("given_name"));
        userMap.put("providerId", userAttributes.get("sub"));
        userMap.put("provider", "Google");
        userMap.put("profilePicture", userAttributes.get("picture"));
        userMap.put("fullname", userAttributes.get("name"));
        userMap.put("email", userAttributes.get("email"));
        return userMap;
    }
}
