package com.merufureku.aromatica.fragrance_service.services;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import com.merufureku.aromatica.fragrance_service.helper.TokenHelper;
import com.merufureku.aromatica.fragrance_service.services.impl.InternalFragranceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalFragranceServiceImplTest {

    @InjectMocks
    private InternalFragranceServiceImpl internalFragranceService;

    @Mock
    private FragrancesRepository fragrancesRepository;

    @Mock
    private TokenHelper tokenHelper;

    private BaseParam baseParam;

    @BeforeEach
    void setUp() {
        baseParam = new BaseParam(1, "tester", "token");
    }

    @Test
    void testGetFragranceNotesBatch_whenIdsProvided_thenReturnFragranceNoteListResponse() {
        var fragranceIds = new HashSet<>(Arrays.asList(1L, 2L));
        var param = new FragranceBatchNotesParam(fragranceIds);

        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        doNothing().when(tokenHelper).validateInternalToken(baseParam.token());
        when(fragrancesRepository.findAllById(fragranceIds)).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAllById(fragranceIds);
    }

    @Test
    void testGetFragranceNotesBatch_whenNoIdsProvided_thenReturnAllFragranceNotes() {
        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        doNothing().when(tokenHelper).validateInternalToken(baseParam.token());
        when(fragrancesRepository.findAll()).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(
                new ExcludeFragranceBatchNotesParam(new HashSet<>()), baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAll();
    }

    @Test
    void testGetFragranceNotesBatch_whenIdsProvided_thenReturnAllFragranceNotes() {
        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        var param = new ExcludeFragranceBatchNotesParam(new HashSet<>(Arrays.asList(1L, 2L)));

        doNothing().when(tokenHelper).validateInternalToken(baseParam.token());
        when(fragrancesRepository.findAllByIdNotIn(param.excludedFragranceIds())).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAllByIdNotIn(new HashSet<>(Arrays.asList(1L, 2L)));
    }
}