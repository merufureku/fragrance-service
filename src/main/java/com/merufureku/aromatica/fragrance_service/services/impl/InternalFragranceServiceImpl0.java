package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IInternalFragranceService;
import org.springframework.stereotype.Service;

@Service
public class InternalFragranceServiceImpl0 implements IInternalFragranceService {
    @Override
    public BaseResponse<FragranceDetailedListResponse> getFragrance(GetFragranceBatchParam param, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(GetFragranceBatchParam param, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(ExcludeFragranceBatchParam param, BaseParam baseParam) {
        return null;
    }
}
