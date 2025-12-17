package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertFragranceNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertFragranceParam;
import com.merufureku.aromatica.fragrance_service.dto.params.UpdateFragranceParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FragranceServiceImpl0 implements IFragranceService {
    @Override
    public BaseResponse<FragranceListResponse> getFragrances(String name, String brand, String gender, String type, String countryOfOrigin, Pageable pageable, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<InsertFragranceResponse> insertFragrance(InsertFragranceParam param, BaseParam baseParam) {
        return null;
    }

    @Override
    public boolean updateFragrance(Long id, UpdateFragranceParam param, BaseParam baseParam) {
        return false;
    }

    @Override
    public boolean deleteFragrance(Long id, BaseParam baseParam) {
        return false;
    }

    @Override
    public BaseResponse<NoteListResponse> getFragranceNotes(Long id, Pageable pageable, BaseParam baseParam) {
        return null;
    }

    @Override
    public boolean updateFragranceNote(Long id, InsertFragranceNoteParam param, BaseParam baseParam) {
        return false;
    }

    @Override
    public boolean deleteFragranceNote(Long id, Long noteId, BaseParam baseParam) {
        return false;
    }
}
