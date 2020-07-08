package com.github.ser.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ser.security")
public class JwtTokenConfig {

    private String secret;
    private int expirationTime = 1000*60*60;
    private String algorithm = "HS512";

}
