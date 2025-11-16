package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;

public record FragranceResponse(Long id, String name, String brand, String type, String countryOfOrigin) {

    public FragranceResponse(Fragrance fragrance){
        this(
                fragrance.getId(),
                fragrance.getName(),
                fragrance.getBrand(),
                fragrance.getType(),
                fragrance.getCountryOfOrigin()
        );
    }
}
