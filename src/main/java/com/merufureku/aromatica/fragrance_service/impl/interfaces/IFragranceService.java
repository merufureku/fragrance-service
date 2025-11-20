package com.merufureku.aromatica.fragrance_service.impl.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import org.springframework.data.domain.Pageable;


public interface IFragranceService {

    BaseResponse<FragranceListResponse> getFragrances(String name, String brand, String gender,
                                                      String type, String countryOfOrigin,
                                                      Pageable pageable, BaseParam baseParam);

    BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam);

    BaseResponse<InsertFragranceResponse> insertFragrance(InsertFragranceParam param, BaseParam baseParam);

    boolean updateFragrance(Long id, UpdateFragranceParam param, BaseParam baseParam);

    boolean deleteFragrance(Long id, BaseParam baseParam);

    BaseResponse<NoteListResponse> getFragranceNotes(Long id, Pageable pageable, BaseParam baseParam);

    boolean updateFragranceNote(Long id, InsertFragranceNoteParam param, BaseParam baseParam);

    boolean deleteFragranceNote(Long id, Long noteId, BaseParam baseParam);
}
