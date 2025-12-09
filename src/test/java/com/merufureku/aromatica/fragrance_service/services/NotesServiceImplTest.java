package com.merufureku.aromatica.fragrance_service.services;

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
import com.merufureku.aromatica.fragrance_service.services.impl.NotesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NotesServiceImplTest {

    @InjectMocks
    private NotesServiceImpl notesServiceImpl;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private SpecificationHelper specificationHelper;

    private BaseParam baseParam;
    private Pageable pageable;
    private InsertNoteParam insertNoteParam;
    private Notes note;

    @BeforeEach
    void setUp(){
        baseParam = new BaseParam(1, "tester", null);
        pageable = PageRequest.of(0, 10);

        insertNoteParam = new InsertNoteParam(
                new HashSet<>(List.of(
                        new InsertNoteParam.NoteRequest("Lavender", "middle")
                ))
        );
        note = Notes.builder()
                .id(1L)
                .name("Vanilla")
                .type("base")
                .build();
    }

    @Test
    void testGetNotes_thenReturnNotes(){

        var notes = Collections.singletonList(note);
        var notePage = new PageImpl<>(notes, pageable, notes.size());

        when(specificationHelper.buildNotesSpecification(any(), any()))
                .thenReturn(mock(Specification.class));
        when(notesRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(notePage);

        BaseResponse<NoteListResponse> response = notesServiceImpl
                .getNotes("Vanilla", "base", pageable, baseParam);

        assertNotNull(response);
        assertNotNull(response.data());
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals(1, response.data().noteResponseList().size());

        verify(notesRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testInsertNotes_thenReturnSuccess(){
        when(notesRepository.existsByName("Lavender")).thenReturn(false);

        BaseResponse<Void> response = notesServiceImpl
                .insertNotes(insertNoteParam, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.status());

        verify(notesRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testInsertNotes_whenParamIsNull_thenThrowException(){
        insertNoteParam = null;

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.insertNotes(insertNoteParam, baseParam));

        assertEquals(CustomStatusEnums.NO_NOTES_TO_INSERT, exception.getCustomStatusEnums());
        verify(notesRepository, never()).saveAll(anyList());
    }

    @Test
    void testInsertNotes_whenParamNotesIsNull_thenThrowException(){
        insertNoteParam = new InsertNoteParam(null);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.insertNotes(insertNoteParam, baseParam));

        assertEquals(CustomStatusEnums.NO_NOTES_TO_INSERT, exception.getCustomStatusEnums());
        verify(notesRepository, never()).saveAll(anyList());
    }

    @Test
    void testInsertNotes_whenParamNotesIsEmpty_thenThrowException(){
        insertNoteParam = new InsertNoteParam(new HashSet<>());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.insertNotes(insertNoteParam, baseParam));

        assertEquals(CustomStatusEnums.NO_NOTES_TO_INSERT, exception.getCustomStatusEnums());
        verify(notesRepository, never()).saveAll(anyList());
    }

    @Test
    void testInsertNotes_whenNoteAlreadyExists_thenThrowException(){
        when(notesRepository.existsByName("Lavender")).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.insertNotes(insertNoteParam, baseParam));

        assertEquals(CustomStatusEnums.NOTE_ALREADY_EXIST, exception.getCustomStatusEnums());
        verify(notesRepository, never()).saveAll(anyList());
    }

    @Test
    void testGetNoteById_thenReturnNote() {
        when(notesRepository.findById(1L)).thenReturn(Optional.ofNullable(note));

        BaseResponse<NoteResponse> response = notesServiceImpl.getNoteById(1L, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertNotNull(response.data());

        verify(notesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNoteById_whenNoteNotExist_thenThrowException() {
        when(notesRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.getNoteById(1L, baseParam));

        assertEquals(CustomStatusEnums.NOTE_NOT_EXIST, exception.getCustomStatusEnums());
        verify(notesRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteNoteById_thenReturnSuccess() {
        when(notesRepository.existsById(1L)).thenReturn(true);

        boolean result = notesServiceImpl.deleteNoteById(1L, baseParam);

        assertTrue(result);
        verify(notesRepository, times(1)).existsById(1L);
        verify(notesRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNoteById_whenNoteNotExist_thenThrowException() {
        when(notesRepository.existsById(1L)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                notesServiceImpl.deleteNoteById(1L, baseParam));

        assertEquals(CustomStatusEnums.NOTE_NOT_EXIST, exception.getCustomStatusEnums());
        verify(notesRepository, times(1)).existsById(1L);
        verify(notesRepository, never()).deleteById(1L);
    }
}
