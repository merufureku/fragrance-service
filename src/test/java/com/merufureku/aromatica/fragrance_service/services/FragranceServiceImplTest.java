package com.merufureku.aromatica.fragrance_service.services;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.entity.FragranceNotesId;
import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragranceNotesRepository;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dao.repository.NotesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import com.merufureku.aromatica.fragrance_service.enums.CustomStatusEnums;
import com.merufureku.aromatica.fragrance_service.exceptions.ServiceException;
import com.merufureku.aromatica.fragrance_service.helper.FragranceHelper;
import com.merufureku.aromatica.fragrance_service.helper.SpecificationHelper;
import com.merufureku.aromatica.fragrance_service.services.impl.FragranceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FragranceServiceImplTest {

    @InjectMocks
    private FragranceServiceImpl fragranceService;

    @Mock
    private FragrancesRepository fragrancesRepository;

    @Mock
    private FragranceNotesRepository fragranceNotesRepository;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private FragranceHelper fragranceHelper;

    @Mock
    private SpecificationHelper specificationHelper;

    private BaseParam baseParam;
    private Fragrance fragrance;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        baseParam = new BaseParam(1, "tester", null);
        pageable = PageRequest.of(0, 10);

        fragrance = Fragrance.builder()
                .id(1L)
                .name("Test Fragrance")
                .brand("Test Brand")
                .description("Test Description")
                .type("Eau de Parfum")
                .countryOfOrigin("France")
                .gender("Unisex")
                .releaseYear(2023)
                .imageUrl("http://test.com/image.jpg")
                .notes(new ArrayList<>())
                .build();
    }

    @Test
    void testGetFragrances_thenReturnFragranceList() {
        var fragrances = Collections.singletonList(fragrance);
        var fragrancePage = new PageImpl<>(fragrances, pageable, fragrances.size());

        when(specificationHelper.buildFragranceSpecification(any(), any(), any(), any(), any()))
                .thenReturn(mock(Specification.class));
        when(fragrancesRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(fragrancePage);

        BaseResponse<FragranceListResponse> response = fragranceService.getFragrances(
                "Test", "Brand", "Unisex", "Parfum", "France", pageable, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Fragrance List Success", response.message());
        assertNotNull(response.data());
        assertEquals(1, response.data().fragranceResponseList().size());

        verify(fragrancesRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetFragrance_whenExists_thenReturnFragranceDetail() {
        when(fragrancesRepository.findById(1L)).thenReturn(Optional.of(fragrance));

        BaseResponse<FragranceDetailedResponse> response = fragranceService.getFragrance(1L, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Fragrance Detail Success", response.message());
        assertNotNull(response.data());

        verify(fragrancesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFragrance_whenNotExists_thenThrowException() {
        when(fragrancesRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.getFragrance(1L, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
        verify(fragrancesRepository, times(1)).findById(1L);
    }

    @Test
    void testInsertFragrance_whenValid_thenInsertSuccessfully() {
        var param = new InsertFragranceParam(
                "New Fragrance", "New Brand", "Description", "Parfum",
                "Italy", "Male", 2024, "http://test.com/new.jpg");

        when(fragrancesRepository.existsByNameAndBrand(param.name(), param.brand()))
                .thenReturn(false);
        when(fragrancesRepository.save(any(Fragrance.class))).thenReturn(fragrance);

        BaseResponse<InsertFragranceResponse> response = fragranceService.insertFragrance(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.status());
        assertEquals("Insert Fragrance Success", response.message());

        verify(fragrancesRepository, times(1)).existsByNameAndBrand(param.name(), param.brand());
        verify(fragrancesRepository, times(1)).save(any(Fragrance.class));
    }

    @Test
    void testInsertFragrance_whenAlreadyExists_thenThrowException() {
        var param = new InsertFragranceParam(
                "Existing", "Brand", "Desc", "Type", "Country", "Gender", 2023, "url");

        when(fragrancesRepository.existsByNameAndBrand(param.name(), param.brand()))
                .thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.insertFragrance(param, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_ALREADY_EXIST, exception.getCustomStatusEnums());
        verify(fragrancesRepository, times(1)).existsByNameAndBrand(param.name(), param.brand());
        verify(fragrancesRepository, never()).save(any(Fragrance.class));
    }

    @Test
    void testInsertFragrance_whenDataIntegrityViolation_thenThrowException() {
        var param = new InsertFragranceParam(
                "New", "Brand", "Desc", "Type", "Country", "Gender", 2023, "url");

        when(fragrancesRepository.existsByNameAndBrand(param.name(), param.brand()))
                .thenReturn(false);
        when(fragrancesRepository.save(any(Fragrance.class)))
                .thenThrow(new DataIntegrityViolationException("Constraint violation"));

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.insertFragrance(param, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_ALREADY_EXIST, exception.getCustomStatusEnums());
    }

    @Test
    void testUpdateFragrance_whenExists_thenUpdateSuccessfully() {
        var param = mock(UpdateFragranceParam.class);
        var updatedFragrance = fragrance;

        when(fragrancesRepository.findById(1L)).thenReturn(Optional.of(fragrance));
        when(fragranceHelper.updateFragrance(fragrance, param)).thenReturn(updatedFragrance);
        when(fragrancesRepository.save(updatedFragrance)).thenReturn(updatedFragrance);

        boolean result = fragranceService.updateFragrance(1L, param, baseParam);

        assertTrue(result);
        verify(fragrancesRepository, times(1)).findById(1L);
        verify(fragranceHelper, times(1)).updateFragrance(fragrance, param);
        verify(fragrancesRepository, times(1)).save(updatedFragrance);
    }

    @Test
    void testUpdateFragrance_whenNotExists_thenThrowException() {
        var param = mock(UpdateFragranceParam.class);
        when(fragrancesRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.updateFragrance(1L, param, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
        verify(fragrancesRepository, times(1)).findById(1L);
        verify(fragranceHelper, never()).updateFragrance(any(), any());
    }

    @Test
    void testDeleteFragrance_whenExists_thenDeleteSuccessfully() {
        when(fragrancesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(fragrancesRepository).deleteById(1L);

        boolean result = fragranceService.deleteFragrance(1L, baseParam);

        assertTrue(result);
        verify(fragrancesRepository, times(1)).existsById(1L);
        verify(fragrancesRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFragrance_whenNotExists_thenThrowException() {
        when(fragrancesRepository.existsById(1L)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.deleteFragrance(1L, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
        verify(fragrancesRepository, times(1)).existsById(1L);
        verify(fragrancesRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetFragranceNotes_whenFragranceExists_thenReturnNotes() {
        var notes = new ArrayList<Notes>();
        fragrance.setNotes(notes);

        when(fragrancesRepository.findById(1L)).thenReturn(Optional.of(fragrance));

        BaseResponse<NoteListResponse> response = fragranceService.getFragranceNotes(1L, pageable, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Fragrance Notes Success", response.message());

        verify(fragrancesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFragranceNotes_whenFragranceNotExists_thenThrowException() {
        when(fragrancesRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.getFragranceNotes(1L, pageable, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
    }

    @Test
    void testUpdateFragranceNote_thenAddNotesToFragrance() {
        var noteRequests = new HashSet<>(Arrays.asList(
                new InsertFragranceNoteParam.FragranceNoteRequest(1L),
                new InsertFragranceNoteParam.FragranceNoteRequest(2L)
        ));

        var param = new InsertFragranceNoteParam(noteRequests);

        var note1 = Notes.builder().id(1L).build();
        var note2 = Notes.builder().id(2L).build();
        var notes = Arrays.asList(note1, note2);

        when(fragrancesRepository.existsById(1L)).thenReturn(true);
        when(notesRepository.findAllById(anyList())).thenReturn(notes);
        when(fragranceNotesRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        boolean result = fragranceService.updateFragranceNote(1L, param, baseParam);

        assertTrue(result);
        verify(fragrancesRepository, times(1)).existsById(1L);
        verify(notesRepository, times(1)).findAllById(anyList());
        verify(fragranceNotesRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testUpdateFragranceNote_whenFragranceNotExists_thenThrowException() {
        var param = new InsertFragranceNoteParam(new HashSet<>());

        when(fragrancesRepository.existsById(1L)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.updateFragranceNote(1L, param, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
    }

    @Test
    void testDeleteFragranceNote_whenExists_thenDeleteSuccessfully() {
        var fragranceNotesId = new FragranceNotesId(1L, 2L);

        when(fragrancesRepository.existsById(1L)).thenReturn(true);
        when(fragranceNotesRepository.existsById(fragranceNotesId)).thenReturn(true);
        doNothing().when(fragranceNotesRepository).deleteById(fragranceNotesId);

        boolean result = fragranceService.deleteFragranceNote(1L, 2L, baseParam);

        assertTrue(result);
        verify(fragrancesRepository, times(1)).existsById(1L);
        verify(fragranceNotesRepository, times(1)).existsById(fragranceNotesId);
        verify(fragranceNotesRepository, times(1)).deleteById(fragranceNotesId);
    }

    @Test
    void testDeleteFragranceNote_whenFragranceNotExists_thenThrowException() {
        when(fragrancesRepository.existsById(1L)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.deleteFragranceNote(1L, 2L, baseParam));

        assertEquals(CustomStatusEnums.FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());
        verify(fragranceNotesRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteFragranceNote_whenNoteNotExists_thenThrowException() {
        var fragranceNotesId = new FragranceNotesId(1L, 2L);

        when(fragrancesRepository.existsById(1L)).thenReturn(true);
        when(fragranceNotesRepository.existsById(fragranceNotesId)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.deleteFragranceNote(1L, 2L, baseParam));

        assertEquals(CustomStatusEnums.NOTE_NOT_EXIST, exception.getCustomStatusEnums());
        verify(fragranceNotesRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteFragranceNote_whenDeletionFails_thenThrowException() {
        var fragranceNotesId = new FragranceNotesId(1L, 2L);

        when(fragrancesRepository.existsById(1L)).thenReturn(true);
        when(fragranceNotesRepository.existsById(fragranceNotesId)).thenReturn(true);
        doThrow(new RuntimeException("Database error"))
                .when(fragranceNotesRepository).deleteById(fragranceNotesId);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> fragranceService.deleteFragranceNote(1L, 2L, baseParam));

        assertEquals(CustomStatusEnums.NOTE_NOT_EXIST, exception.getCustomStatusEnums());
    }
}