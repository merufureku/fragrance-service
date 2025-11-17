package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.InsertFragranceResponse;
import org.springframework.data.domain.Pageable;


public interface IFragranceService {

    BaseResponse<FragranceListResponse> getFragrances(String name, String brand, String gender,
                                                      String type, String countryOfOrigin,
                                                      Pageable pageable, BaseParam baseParam);

    BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam);

    BaseResponse<InsertFragranceResponse> insertFragrance(InsertFragranceParam param, BaseParam baseParam);

    boolean updateFragrance(Long id, UpdateFragranceParam param, BaseParam baseParam);

    boolean deleteFragrance(Long id, BaseParam baseParam);

    boolean updateFragranceNotes(Long id, InsertFragranceNoteParam param, BaseParam baseParam);
}
