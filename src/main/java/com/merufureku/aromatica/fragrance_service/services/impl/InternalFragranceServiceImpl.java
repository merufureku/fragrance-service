package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.params.FragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;
import com.merufureku.aromatica.fragrance_service.helper.TokenHelper;
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
    private final TokenHelper tokenHelper;

    public InternalFragranceServiceImpl(FragrancesRepository fragrancesRepository, TokenHelper tokenHelper) {
        this.fragrancesRepository = fragrancesRepository;
        this.tokenHelper = tokenHelper;
    }


    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(FragranceBatchNotesParam param, BaseParam baseParam) {
        logger.info("Fetching Fragrance Notes for the following IDs: {}", param.fragranceIds());

        tokenHelper.validateInternalToken(baseParam.token());

        var fragranceToGet = fragrancesRepository.findAllById(param.fragranceIds());

        var noteResponseList = fragranceToGet.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(ExcludeFragranceBatchNotesParam param, BaseParam baseParam) {
        tokenHelper.validateInternalToken(baseParam.token());

        logger.info("Fetching Fragrance Notes for all fragrances");

        List<Fragrance> fragranceToGet;

        if (param == null || param.excludedFragranceIds().isEmpty()) {
            fragranceToGet = fragrancesRepository.findAll();
        }
        else{
            fragranceToGet = fragrancesRepository.findAllByIdNotIn(param.excludedFragranceIds());
        }

        var noteResponseList = fragranceToGet.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }
}
