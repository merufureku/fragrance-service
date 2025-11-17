package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record FragranceDetailedResponse(Long id, String name, String brand,
                                        String description, String gender, int releaseYear,
                                        String imageUrl, Map<String, List<String>> notes) {

    public FragranceDetailedResponse(Fragrance fragrance){
        this(
            fragrance.getId(),
            fragrance.getName(),
            fragrance.getBrand(),
            fragrance.getDescription(),
            fragrance.getGender(),
            fragrance.getReleaseYear(),
            fragrance.getImageUrl(),
            fragrance.getNotes().stream()
                .collect(Collectors.groupingBy(
                    Notes::getType,
                    Collectors.mapping(Notes::getName, Collectors.toList())
                ))
        );
    }
}
