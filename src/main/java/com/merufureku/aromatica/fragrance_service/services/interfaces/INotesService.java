package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteListResponse;
import org.springframework.data.domain.Pageable;

public interface INotesService {

    BaseResponse<NoteListResponse> getNotes(String name, String type, Pageable pageable, BaseParam baseParam);

    BaseResponse<Void> insertNotes(InsertNoteParam param, BaseParam baseParam);

}
