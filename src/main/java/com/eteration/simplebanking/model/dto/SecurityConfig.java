package com.eteration.simplebanking.model.dto;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public record SecurityConfig(
    String algorithm,
    String secretKey,
    SecretKeySpec secretKeySpec
) {
    
    public SecurityConfig(String algorithm, String secretKey) {
        this(algorithm, secretKey, new SecretKeySpec(
            normalizeKey(secretKey).getBytes(StandardCharsets.UTF_8), 
            getFullAlgorithmName(algorithm)));
    }
    
    private static String getFullAlgorithmName(String algorithm) {
        if ("AES".equalsIgnoreCase(algorithm)) {
            return "AES";
        }
        return algorithm;
    }
    
    private static String normalizeKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Secret key cannot be null or empty");
        }
        
        // UUID formatındaki tire'leri çıkar
        String cleanKey = key.replace("-", "");
        
        // 32 byte'a normalize et
        if (cleanKey.length() > 32) {
            return cleanKey.substring(0, 32);
        } else if (cleanKey.length() < 32) {
            // 32'ye tamamla
            return String.format("%-32s", cleanKey).replace(' ', '0');
        }
        return cleanKey;
    }
} 