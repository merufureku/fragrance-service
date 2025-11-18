package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteResponse;
import org.springframework.data.domain.Pageable;

public interface INotesService {

    BaseResponse<NoteListResponse> getNotes(String name, String type, Pageable pageable, BaseParam baseParam);

    BaseResponse<Void> insertNotes(InsertNoteParam param, BaseParam baseParam);

    BaseResponse<NoteResponse> getNoteById(Long noteId, BaseParam baseParam);

    boolean deleteNoteById(Long noteId, BaseParam baseParam);
}
