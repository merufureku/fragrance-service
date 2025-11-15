package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrances;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceResponse;
import com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums;
import com.merufureku.aromatica.fragrance_service.exceptions.ServiceException;
import com.merufureku.aromatica.fragrance_service.helper.SpecificationHelper;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FragranceServiceImpl implements IFragranceService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final FragrancesRepository fragrancesRepository;
    private final SpecificationHelper specificationHelper;

    public FragranceServiceImpl(FragrancesRepository fragrancesRepository,
                                SpecificationHelper specificationHelper) {
        this.fragrancesRepository = fragrancesRepository;
        this.specificationHelper = specificationHelper;
    }

    @Override
    public BaseResponse<FragranceListResponse> getFragrances(String name, String brand, String gender,
                                                             String type, String countryOfOrigin,
                                                             Pageable pageable, BaseParam baseParam) {

        var fragrancesPage = fragrancesRepository.findAll(
                specificationHelper.buildFragranceSpecification(
                        name, brand, gender, type, countryOfOrigin),
                pageable
        );

        var fragranceResponseList = fragrancesPage.getContent().stream()
                .map(FragranceResponse::new)
                .toList();

        var response = new FragranceListResponse(fragranceResponseList, fragrancesPage);
        return new BaseResponse<>(HttpStatus.OK.value(), "Get Fragrance List Success", response);
    }

    @Override
    public BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam) {
        logger.info("Fetching fragrance details for ID: {}", id);

        var fragrance = fragrancesRepository.findById(id)
                .orElseThrow(() -> new ServiceException(CustomStatusEnums.FRAGRANCE_NOT_FOUND));

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Fragrance Detail Success", new FragranceDetailedResponse(fragrance));
    }
}
