package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IInternalFragranceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternalFragranceServiceImpl implements IInternalFragranceService {


    private final Logger logger = LogManager.getLogger(this.getClass());

    private final FragrancesRepository fragrancesRepository;

    public InternalFragranceServiceImpl(FragrancesRepository fragrancesRepository) {
        this.fragrancesRepository = fragrancesRepository;
    }

    @Override
    public BaseResponse<FragranceDetailedListResponse> getFragrance(GetFragranceBatchParam param, BaseParam baseParam) {
        logger.info("Fetching Fragrance Details for the following IDs: {}", param.fragranceIds());

        var fragrance = fragrancesRepository.findAllById(param.fragranceIds());

        var fragranceResponseList = fragrance.stream()
                .map(FragranceDetailedListResponse.FragranceDetailedResponse::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Metadata Success",
                new FragranceDetailedListResponse(fragranceResponseList));
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(GetFragranceBatchParam param, BaseParam baseParam) {
        logger.info("Fetching Fragrance Notes for the following IDs: {}", param.fragranceIds());

        var fragrances = fragrancesRepository.findAllById(param.fragranceIds());

        var noteResponseList = fragrances.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(ExcludeFragranceBatchParam param, BaseParam baseParam) {

        logger.info("Fetching Fragrance Notes for all fragrances");

        List<Fragrance> fragrances;

        if (param == null || param.excludedFragranceIds().isEmpty()) {
            fragrances = fragrancesRepository.findAll();
        }
        else{
            fragrances = fragrancesRepository.findAllByIdNotIn(param.excludedFragranceIds());
        }

        var noteResponseList = fragrances.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }
}
