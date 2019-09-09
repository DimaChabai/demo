package com.example.demo.config;



import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import java.util.Map;

public class CustomPrincipalExtractor implements PrincipalExtractor {
    private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username", "userid", "user_id", "login","name", "id" };

    public CustomPrincipalExtractor() {
    }

    public Object extractPrincipal(Map<String, Object> map) {
        String[] var2 = PRINCIPAL_KEYS;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            if (map.containsKey(key)) {
                return map.get(key);
            }
        }

        return null;
    }
}
