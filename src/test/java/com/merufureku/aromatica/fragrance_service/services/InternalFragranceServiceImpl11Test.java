package com.merufureku.aromatica.fragrance_service.services;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.helper.TokenHelper;
import com.merufureku.aromatica.fragrance_service.services.impl.InternalFragranceServiceImpl1;
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
class InternalFragranceServiceImpl11Test {

    @InjectMocks
    private InternalFragranceServiceImpl1 internalFragranceService;

    @Mock
    private FragrancesRepository fragrancesRepository;

    @Mock
    private TokenHelper tokenHelper;

    private BaseParam baseParam;

    @BeforeEach
    void setUp() {
        baseParam = new BaseParam(1, "tester");
    }

    @Test
    void testGetFragranceNotesBatch_whenIdsProvided_thenReturnFragranceNoteListResponse() {
        var fragranceIds = new HashSet<>(Arrays.asList(1L, 2L));
        var param = new GetFragranceBatchParam(fragranceIds);

        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        when(fragrancesRepository.findAllByIdWithNotes(fragranceIds)).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAllByIdWithNotes(fragranceIds);
    }

    @Test
    void testGetFragranceNotesBatch_whenNoIdsProvided_thenReturnAllFragranceNotes() {
        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        when(fragrancesRepository.findAllWithNotes()).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(
                new ExcludeFragranceBatchParam(new HashSet<>()), baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAllWithNotes();
    }

    @Test
    void testGetFragranceNotesBatch_whenIdsProvided_thenReturnAllFragranceNotes() {
        var fragrance1 = Fragrance.builder().id(1L).notes(new ArrayList<>()).build();
        var fragrance2 = Fragrance.builder().id(2L).notes(new ArrayList<>()).build();
        var fragrances = Arrays.asList(fragrance1, fragrance2);

        var param = new ExcludeFragranceBatchParam(new HashSet<>(Arrays.asList(1L, 2L)));

        when(fragrancesRepository.findAllByIdNotInWithNotes(param.excludedFragranceIds())).thenReturn(fragrances);

        var response = internalFragranceService.getFragranceNotes(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Notes Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragranceNoteLists().size());

        verify(fragrancesRepository, times(1)).findAllByIdNotInWithNotes(new HashSet<>(Arrays.asList(1L, 2L)));
    }

    @Test
    void testGetFragranceBatch_whenIdsProvided_thenReturnFragranceDetailedListResponse() {
        var fragranceIds = new HashSet<>(Arrays.asList(1L, 2L));
        var param = new GetFragranceBatchParam(fragranceIds);

        var fragrance1 = Fragrance.builder()
                .id(1L)
                .name("Fragrance One")
                .brand("Brand A")
                .description("Description One")
                .notes(new ArrayList<>())
                .build();

        var fragrance2 = Fragrance.builder()
                .id(2L)
                .name("Fragrance Two")
                .brand("Brand B")
                .description("Description Two")
                .notes(new ArrayList<>())
                .build();

        var fragrances = Arrays.asList(fragrance1, fragrance2);

        when(fragrancesRepository.findAllByIdWithNotes(fragranceIds)).thenReturn(fragrances);

        var response = internalFragranceService.getFragrance(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Metadata Success", response.message());
        assertNotNull(response.data());
        assertEquals(2, response.data().fragrances().size());

        var firstResponse = response.data().fragrances().get(0);
        assertEquals(fragrance1.getId(), firstResponse.fragranceId());
        assertEquals(fragrance1.getName(), firstResponse.name());
        assertEquals(fragrance1.getBrand(), firstResponse.brand());
        assertEquals(fragrance1.getDescription(), firstResponse.description());

        verify(fragrancesRepository, times(1)).findAllByIdWithNotes(fragranceIds);
    }

    @Test
    void testGetFragranceBatch_whenRepositoryReturnsEmpty_thenReturnEmptyList() {
        var fragranceIds = new HashSet<>(Collections.singletonList(1L));
        var param = new GetFragranceBatchParam(fragranceIds);

        when(fragrancesRepository.findAllByIdWithNotes(fragranceIds)).thenReturn(Collections.emptyList());

        var response = internalFragranceService.getFragrance(param, baseParam);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.status());
        assertEquals("Get Batch Fragrance Metadata Success", response.message());
        assertNotNull(response.data());
        assertTrue(response.data().fragrances().isEmpty());

        verify(fragrancesRepository, times(1)).findAllByIdWithNotes(fragranceIds);
    }
}