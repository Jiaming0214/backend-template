package com.ming.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfiguration {
    private String secret;
    private Long expiration;
    private String header;
    private String tokenPrefix;
}
