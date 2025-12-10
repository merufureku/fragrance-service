package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;

public interface IInternalFragranceService {

    BaseResponse<FragranceDetailedListResponse> getFragrance(GetFragranceBatchParam param, BaseParam baseParam);

    BaseResponse<FragranceNoteListResponse> getFragranceNotes(GetFragranceBatchParam param, BaseParam baseParam);

    BaseResponse<FragranceNoteListResponse> getFragranceNotes(ExcludeFragranceBatchParam param, BaseParam baseParam);

}
