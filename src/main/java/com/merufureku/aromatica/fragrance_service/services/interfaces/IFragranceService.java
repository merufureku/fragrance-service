package com.merufureku.aromatica.fragrance_service.services.interfaces;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceListResponse;
import org.springframework.data.domain.Pageable;


public interface IFragranceService {

    BaseResponse<FragranceListResponse> getFragrances(String name, String brand, String gender,
                                                      String type, String countryOfOrigin,
                                                      Pageable pageable, BaseParam baseParam);

    BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam);
}
