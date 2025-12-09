package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.params.ExcludeFragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.params.FragranceBatchNotesParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceNoteListResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IInternalFragranceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InternalFragranceController {

    private final IInternalFragranceService internalFragranceService;

    public InternalFragranceController(IInternalFragranceService internalFragranceService) {
        this.internalFragranceService = internalFragranceService;
    }


    @PostMapping("/internal/fragrances/batch/notes")
    @Operation(summary = "Get Selected Fragrance Batch Notes")
    public ResponseEntity<BaseResponse<FragranceNoteListResponse>> getFragranceBatchNotes(
            @RequestBody FragranceBatchNotesParam param,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId, authorization.substring(7));

        var response = internalFragranceService.getFragranceNotes(param, baseParam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/internal/fragrances/exclude/notes")
    @Operation(summary = "Get Filtered Fragrance Batch Notes")
    public ResponseEntity<BaseResponse<FragranceNoteListResponse>> getFragranceBatchNotes(
            @RequestBody(required = false) ExcludeFragranceBatchNotesParam param,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId, authorization.substring(7));

        var response = internalFragranceService.getFragranceNotes(param, baseParam);
        return ResponseEntity.ok(response);
    }
}
