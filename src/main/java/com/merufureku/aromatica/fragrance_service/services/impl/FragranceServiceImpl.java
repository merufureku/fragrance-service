package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.entity.FragranceNotes;
import com.merufureku.aromatica.fragrance_service.dao.entity.FragranceNotesId;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragranceNotesRepository;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dao.repository.NotesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.*;
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

import static com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class FragranceServiceImpl implements IFragranceService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final FragrancesRepository fragrancesRepository;
    private final FragranceNotesRepository fragranceNotesRepository;
    private final NotesRepository notesRepository;
    private final FragranceHelper fragranceHelper;
    private final SpecificationHelper specificationHelper;

    public FragranceServiceImpl(FragrancesRepository fragrancesRepository, FragranceNotesRepository fragranceNotesRepository, NotesRepository notesRepository, FragranceHelper fragranceHelper,
                                SpecificationHelper specificationHelper) {
        this.fragrancesRepository = fragrancesRepository;
        this.fragranceNotesRepository = fragranceNotesRepository;
        this.notesRepository = notesRepository;
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

    @Override
    public BaseResponse<NoteListResponse> getFragranceNotes(Long id, Pageable pageable, BaseParam baseParam) {
        logger.info("Fetching Fragrance Notes for ID: {}", id);

        var fragranceToGet = fragrancesRepository.findById(id)
                .orElseThrow(() -> new ServiceException(FRAGRANCE_NOT_FOUND));

        var response = fragranceToGet.getNotes().stream()
                .map(NoteResponse::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Fragrance Notes Success",
                new NoteListResponse(response, pageable));
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(FragranceBatchNotesParam param, BaseParam baseParam) {
        logger.info("Fetching Fragrance Notes for the following IDs: {}", param.fragranceIds());

        var fragranceToGet = fragrancesRepository.findAllById(param.fragranceIds());

        var noteResponseList = fragranceToGet.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }

    @Override
    public BaseResponse<FragranceNoteListResponse> getFragranceNotes(BaseParam baseParam) {
        logger.info("Fetching Fragrance Notes for all fragrances");

        var fragranceToGet = fragrancesRepository.findAll();

        var noteResponseList = fragranceToGet.stream()
                .map(FragranceNoteListResponse.FragranceNoteList::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Batch Fragrance Notes Success",
                new FragranceNoteListResponse(noteResponseList));
    }

    @Override
    public boolean updateFragranceNote(Long id, InsertFragranceNoteParam param, BaseParam baseParam) {
        logger.info("Updating Fragrance Notes for ID: {}", id);

        if (!fragrancesRepository.existsById(id)){
            throw new ServiceException(FRAGRANCE_NOT_FOUND);
        }

        var noteList = notesRepository.findAllById(param.notes().stream()
                .map(InsertFragranceNoteParam.FragranceNoteRequest::id).toList());

        var newNotesToInsert = noteList.stream().map(
                note -> FragranceNotes.builder()
                        .id(new FragranceNotesId(id, note.getId()))
                        .build())
                .toList();

        logger.info("Saving {} fragrance notes for fragrance ID: {}", newNotesToInsert.size(), id);
        fragranceNotesRepository.saveAll(newNotesToInsert);

        return true;
    }

    @Override
    public boolean deleteFragranceNote(Long id, Long noteId, BaseParam baseParam) {
        logger.info("Removing Note ID for {} fragrance: {}", noteId, id);

        if (!fragrancesRepository.existsById(id)){
            throw new ServiceException(FRAGRANCE_NOT_FOUND);
        }

        var fragranceNoteId = new FragranceNotesId(id, noteId);

        if (!fragranceNotesRepository.existsById(fragranceNoteId)) {
            throw new ServiceException(CustomStatusEnums.NOTE_NOT_EXIST);
        }

        try{
            fragranceNotesRepository.deleteById(fragranceNoteId);

            logger.info("Successfully removed Note ID {} from Fragrance ID {}", noteId, id);
            return true;
        }
        catch (Exception e){
            logger.error("Error occurred while deleting fragrance note: {}", e.getMessage());
            throw new ServiceException(CustomStatusEnums.NOTE_NOT_EXIST);
        }
    }
}
