package com.merufureku.aromatica.fragrance_service.dto.params;

public record InsertFragranceParam(String name, String brand, String description,
                                   String gender, int releaseYear, String text) {}
