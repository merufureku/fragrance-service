package com.merufureku.aromatica.fragrance_service.dto.params;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record InsertFragranceNoteParam(Set<FragranceNoteRequest> notes) {

    public record FragranceNoteRequest(
            @NotBlank
            Long id
    ) {}
}

