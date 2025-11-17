package com.merufureku.aromatica.fragrance_service.dto.params;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record InsertNoteParam(Set<NoteRequest> notes) {

    public record NoteRequest(
            @NotBlank
            String name,

            @NotBlank
            @Pattern(regexp = "^(?i:top|mid|base)$", message = "Invalid note type. Must be 'top', 'mid', or 'base'.")
            String type
    ) {}
}

