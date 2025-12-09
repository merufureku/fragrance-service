package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.params.FragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;

public interface IInternalFragranceService {

    BaseResponse<FragranceNoteListResponse> getFragranceNotes(FragranceBatchNotesParam param, BaseParam baseParam);

    BaseResponse<FragranceNoteListResponse> getFragranceNotes(ExcludeFragranceBatchNotesParam param, BaseParam baseParam);

}
