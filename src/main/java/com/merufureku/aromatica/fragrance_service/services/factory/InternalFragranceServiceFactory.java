package com.merufureku.aromatica.fragrance_service.services.factory;

import com.merufureku.aromatica.fragrance_service.services.impl.InternalFragranceServiceImpl0;
import com.merufureku.aromatica.fragrance_service.services.impl.InternalFragranceServiceImpl1;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IInternalFragranceService;
import org.springframework.stereotype.Component;

@Component
public class InternalFragranceServiceFactory {

    private final InternalFragranceServiceImpl0 internalFragranceServiceImpl0;
    private final InternalFragranceServiceImpl1 internalFragranceServiceImpl1;

    public InternalFragranceServiceFactory(InternalFragranceServiceImpl0 internalFragranceServiceImpl0, InternalFragranceServiceImpl1 internalFragranceServiceImpl1) {
        this.internalFragranceServiceImpl0 = internalFragranceServiceImpl0;
        this.internalFragranceServiceImpl1 = internalFragranceServiceImpl1;
    }


    public IInternalFragranceService getService(int version) {
        return switch (version) {
            case 0 -> internalFragranceServiceImpl0;
            case 1 -> internalFragranceServiceImpl1;
            default -> throw new IllegalArgumentException("Unsupported service version: " + version);
        };
    }
}
