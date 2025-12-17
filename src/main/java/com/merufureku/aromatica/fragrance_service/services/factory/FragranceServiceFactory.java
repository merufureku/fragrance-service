package com.merufureku.aromatica.fragrance_service.services.factory;

import com.merufureku.aromatica.fragrance_service.services.impl.FragranceServiceImpl0;
import com.merufureku.aromatica.fragrance_service.services.impl.FragranceServiceImpl1;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import org.springframework.stereotype.Component;

@Component
public class FragranceServiceFactory {

    private final FragranceServiceImpl0 fragranceServiceImpl0;
    private final FragranceServiceImpl1 fragranceServiceImpl1;

    public FragranceServiceFactory(FragranceServiceImpl0 fragranceServiceImpl0, FragranceServiceImpl1 fragranceServiceImpl1) {
        this.fragranceServiceImpl0 = fragranceServiceImpl0;
        this.fragranceServiceImpl1 = fragranceServiceImpl1;
    }

    public IFragranceService getService(int version) {
        return switch (version) {
            case 0 -> fragranceServiceImpl0;
            case 1 -> fragranceServiceImpl1;
            default -> throw new IllegalArgumentException("Unsupported recommendation service version: " + version);
        };
    }
}
