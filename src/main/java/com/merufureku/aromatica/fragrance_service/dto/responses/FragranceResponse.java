package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrances;

public record FragranceResponse(Long id, String name, String brand, String type, String countryOfOrigin) {

    public FragranceResponse(Fragrances fragrances){
        this(
                fragrances.getId(),
                fragrances.getName(),
                fragrances.getBrand(),
                fragrances.getType(),
                fragrances.getCountryOfOrigin()
        );
    }
}
