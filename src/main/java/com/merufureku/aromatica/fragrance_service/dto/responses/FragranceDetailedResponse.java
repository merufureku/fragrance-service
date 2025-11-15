package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrances;

public record FragranceDetailedResponse(Long id, String name, String brand,
                                        String description, String gender, int releaseYear,
                                        String imageUrl) {

    public FragranceDetailedResponse(Fragrances fragrances){
        this(
            fragrances.getId(),
            fragrances.getName(),
            fragrances.getBrand(),
            fragrances.getDescription(),
            fragrances.getGender(),
            fragrances.getReleaseYear(),
            fragrances.getImageUrl()
        );
    }
}
