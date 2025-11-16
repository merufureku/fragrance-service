package com.merufureku.aromatica.fragrance_service.dto.params;

public record UpdateFragranceParam(String brand, String description,
                                   String type, String countryOfOrigin, String gender,
                                   Integer releaseYear, String text) {}
