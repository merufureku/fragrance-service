package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;

public record InsertFragranceResponse(Long id, String name) {

    public InsertFragranceResponse(Fragrance fragrance){
        this(
                fragrance.getId(),
                fragrance.getName()
        );
    }
}
