package com.lucius.bitgain.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "psy.jwt")
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
