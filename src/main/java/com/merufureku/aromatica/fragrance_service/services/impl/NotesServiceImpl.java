package com.merufureku.aromatica.fragrance_service.services.impl;

import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;
import com.merufureku.aromatica.fragrance_service.dao.repository.NotesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.InsertNoteParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.NoteResponse;
import com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums;
import com.merufureku.aromatica.fragrance_service.exceptions.ServiceException;
import com.merufureku.aromatica.fragrance_service.helper.SpecificationHelper;
import com.merufureku.aromatica.fragrance_service.services.interfaces.INotesService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums.NOTE_ALREADY_EXIST;
import static com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums.NO_NOTES_TO_INSERT;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotesServiceImpl implements INotesService {

    private final NotesRepository notesRepository;
    private final SpecificationHelper specificationHelper;

    public NotesServiceImpl(NotesRepository notesRepository, SpecificationHelper specificationHelper) {
        this.notesRepository = notesRepository;
        this.specificationHelper = specificationHelper;
    }

    @Override
    public BaseResponse<NoteListResponse> getNotes(String name, String type, Pageable pageable, BaseParam baseParam) {

        var notes = notesRepository.findAll(
                specificationHelper.buildNotesSpecification(
                        name, type),
                pageable
        );

        var noteResponseList = notes.getContent().stream()
                .map(NoteResponse::new)
                .toList();

        return new BaseResponse<>(HttpStatus.OK.value(), "Get Note List Success",
                new NoteListResponse(noteResponseList, pageable));
    }

    @Override
    public BaseResponse<Void> insertNotes(InsertNoteParam param, BaseParam baseParam) {
        if (param == null || param.notes() == null || param.notes().isEmpty()) {
            throw new ServiceException(NO_NOTES_TO_INSERT);
        }

        for (InsertNoteParam.NoteRequest noteRequest : param.notes()) {
            if (notesRepository.existsByName(noteRequest.name())) {
                CustomStatusEnums customStatusEnums = NOTE_ALREADY_EXIST;
                customStatusEnums.setMessage(NOTE_ALREADY_EXIST.getMessage().replace("{}", noteRequest.name()));

                throw new ServiceException(customStatusEnums);
            }
        }

        List<Notes> notesToSave = param.notes().stream()
                .map(newNote -> Notes.builder()
                        .name(newNote.name())
                        .type(newNote.type())
                        .build())
                .toList();

        notesRepository.saveAll(notesToSave);

        return new BaseResponse<>(HttpStatus.CREATED.value(), "Insert Notes Success", null);
    }
}
