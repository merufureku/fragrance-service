package com.merufureku.aromatica.fragrance_service.dto.params;

import jakarta.validation.constraints.NotBlank;

public record InsertFragranceParam(
        @NotBlank(message = "Fragrance name cannot be null") String name,
        @NotBlank(message = "Fragrance brand cannot be null") String brand,
        @NotBlank(message = "Fragrance description cannot be null") String description,
        @NotBlank(message = "Fragrance type cannot be null") String type,
        @NotBlank(message = "Fragrance origin cannot be null") String countryOfOrigin,
        @NotBlank(message = "Fragrance gender cannot be null") String gender,
        @NotBlank(message = "Fragrance release year cannot be null") Integer releaseYear,
        String text
) {}
