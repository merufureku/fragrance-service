package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.INotesService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotesServiceImpl0 implements INotesService {
    @Override
    public BaseResponse<NoteListResponse> getNotes(String name, String type, Pageable pageable, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<Void> insertNotes(InsertNoteParam param, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<NoteResponse> getNoteById(Long noteId, BaseParam baseParam) {
        return null;
    }

    @Override
    public boolean deleteNoteById(Long noteId, BaseParam baseParam) {
        return false;
    }
}
