package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;

public record FragranceDetailedResponse(Long id, String name, String brand,
                                        String description, String gender, int releaseYear,
                                        String imageUrl) {

    public FragranceDetailedResponse(Fragrance fragrance){
        this(
            fragrance.getId(),
            fragrance.getName(),
            fragrance.getBrand(),
            fragrance.getDescription(),
            fragrance.getGender(),
            fragrance.getReleaseYear(),
            fragrance.getImageUrl()
        );
    }
}
