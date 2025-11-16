package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertFragranceParam;
import com.merufureku.aromatica.fragrance_service.dto.params.UpdateFragranceParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums;
import com.merufureku.aromatica.fragrance_service.exceptions.ServiceException;
import com.merufureku.aromatica.fragrance_service.helper.FragranceHelper;
import com.merufureku.aromatica.fragrance_service.helper.SpecificationHelper;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums.FRAGRANCE_ALREADY_EXIST;
import static com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums.FRAGRANCE_NOT_FOUND;

@Service
@Transactional
public class FragranceServiceImpl implements IFragranceService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final FragrancesRepository fragrancesRepository;
    private final FragranceHelper fragranceHelper;
    private final SpecificationHelper specificationHelper;

    public FragranceServiceImpl(FragrancesRepository fragrancesRepository, FragranceHelper fragranceHelper,
                                SpecificationHelper specificationHelper) {
        this.fragrancesRepository = fragrancesRepository;
        this.fragranceHelper = fragranceHelper;
        this.specificationHelper = specificationHelper;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public BaseResponse<FragranceDetailedResponse> getFragrance(long id, BaseParam baseParam) {
        logger.info("Fetching fragrance details for ID: {}", id);

        var fragrance = fragrancesRepository.findById(id)
                .orElseThrow(() -> new ServiceException(CustomStatusEnums.FRAGRANCE_NOT_FOUND));

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Fragrance Detail Success", new FragranceDetailedResponse(fragrance));
    }

    @Override
    public BaseResponse<InsertFragranceResponse> insertFragrance(InsertFragranceParam param, BaseParam baseParam) {

        logger.info("Inserting new fragrance: {}", param.name());

        if (fragrancesRepository.existsByNameAndBrand(param.name(), param.brand())){
            throw new ServiceException(FRAGRANCE_ALREADY_EXIST);
        }

        var newFragrance = Fragrance.builder()
                .name(param.name())
                .brand(param.brand())
                .description(param.description())
                .type(param.type())
                .countryOfOrigin(param.countryOfOrigin())
                .gender(param.gender())
                .releaseYear(param.releaseYear())
                .imageUrl(param.text())
                .build();

        try {
            var savedFragrance = fragrancesRepository.save(newFragrance);
            return new BaseResponse<>(HttpStatus.CREATED.value(), "Insert Fragrance Success",
                    new InsertFragranceResponse(savedFragrance));

        } catch (DataIntegrityViolationException ex) {
            logger.error("Data integrity violation while inserting fragrance: {}", ex.getMessage());
            throw new ServiceException(FRAGRANCE_ALREADY_EXIST);
        }
    }

    @Override
    public boolean updateFragrance(Long id, UpdateFragranceParam param, BaseParam baseParam) {
        logger.info("Updating existing fragrance: {}", id);

        var fragranceToUpdate = fragrancesRepository.findById(id)
                .orElseThrow(() -> new ServiceException(FRAGRANCE_NOT_FOUND));

        var updatedFragrance = fragranceHelper.updateFragrance(fragranceToUpdate, param);

        fragrancesRepository.save(updatedFragrance);
        return true;
    }

    @Override
    public boolean deleteFragrance(Long id, BaseParam baseParam) {
        logger.info("Deleting existing fragrance: {}", id);

        if (!fragrancesRepository.existsById(id)){
            throw new ServiceException(FRAGRANCE_NOT_FOUND);
        }

        fragrancesRepository.deleteById(id);
        return true;
    }
}
