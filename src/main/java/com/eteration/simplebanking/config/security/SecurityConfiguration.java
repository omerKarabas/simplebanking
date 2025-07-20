package com.eteration.simplebanking.config.security;

import com.eteration.simplebanking.model.dto.SecurityConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {
    
    @Bean
    public SecurityConfig securityConfig(SecurityProperties securityProperties) {
        return new SecurityConfig(
            securityProperties.getAlgorithm(),
            securityProperties.getSecretKey()
        );
    }
} 