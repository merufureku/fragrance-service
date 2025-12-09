package com.merufureku.aromatica.fragrance_service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class KeyConfig {

    @Value("${jwt.access.secret.key}")
    private String jwtAccessSecretKey;

    @Value("${jwt.internal.fragrance.secret.key}")
    private String jwtInternalFragranceSecretKey;

}
