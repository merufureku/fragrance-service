package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;

public record NoteResponse(Long id, String name, String type) {

    public NoteResponse(Notes notes) {
        this(notes.getId(), notes.getName(), notes.getType());
    }
}
