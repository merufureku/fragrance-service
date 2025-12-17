package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;
import com.merufureku.aromatica.fragrance_service.services.factory.InternalFragranceServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/fragrances")
public class InternalFragranceController {

    private final InternalFragranceServiceFactory internalFragranceServiceFactory;

    public InternalFragranceController(InternalFragranceServiceFactory internalFragranceServiceFactory) {
        this.internalFragranceServiceFactory = internalFragranceServiceFactory;
    }

    @PostMapping("/batch/full")
    @Operation(summary = "Get Selected Fragrance Batch Details")
    public ResponseEntity<BaseResponse<FragranceDetailedListResponse>> getFragranceBatch(
            @RequestBody GetFragranceBatchParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        var response = internalFragranceServiceFactory.getService(version).getFragrance(param, baseParam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch/notes")
    @Operation(summary = "Get Selected Fragrance Batch Notes")
    public ResponseEntity<BaseResponse<FragranceNoteListResponse>> getFilteredFragranceNotes(
            @RequestBody GetFragranceBatchParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        var response = internalFragranceServiceFactory.getService(version).getFragranceNotes(param, baseParam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exclude/notes")
    @Operation(summary = "Get Filtered Fragrance Batch Notes")
    public ResponseEntity<BaseResponse<FragranceNoteListResponse>> getFragranceNotesExcluding(
            @RequestBody(required = false) ExcludeFragranceBatchParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        var response = internalFragranceServiceFactory.getService(version).getFragranceNotes(param, baseParam);
        return ResponseEntity.ok(response);
    }
}
