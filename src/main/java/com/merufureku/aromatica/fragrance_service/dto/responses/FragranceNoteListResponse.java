package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;

import java.util.List;

public record FragranceNoteListResponse(List<FragranceNoteList> fragranceNoteLists) {

    public record FragranceNoteList(Long fragranceId, List<NoteResponse> noteResponseList) {

        public FragranceNoteList(Fragrance fragrance){
            this(
                fragrance.getId(),
                fragrance.getNotes().stream()
                        .map(NoteResponse::new)
                        .toList()
            );
        }

    }

}
