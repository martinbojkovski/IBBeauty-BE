package com.mua.ibbeauty.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt")
public class JwtConfigurationProperties {
    private String secretKey;
    private int expirationTimeInMinutes;
}
