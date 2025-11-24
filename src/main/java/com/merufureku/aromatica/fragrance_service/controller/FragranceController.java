package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.*;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FragranceController {

    private final IFragranceService fragranceService;

    public FragranceController(IFragranceService fragranceService) {
        this.fragranceService = fragranceService;
    }

    @GetMapping("/public/fragrances")
    @Operation(summary = "Get list of fragrances")
    public ResponseEntity<BaseResponse<FragranceListResponse>> getFragrances(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String country,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId,
            Pageable pageable) {

        var baseParam = new BaseParam(version, correlationId);
        var response = fragranceService.getFragrances(name, brand, gender, type,
                country, pageable, baseParam);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/fragrances/{id}")
    @Operation(summary = "Get Fragrance")
    public ResponseEntity<BaseResponse<FragranceDetailedResponse>> getFragrance(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = fragranceService.getFragrance(id, baseParam);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/fragrances")
    @Operation(summary = "Insert Fragrance")
    public ResponseEntity<BaseResponse<InsertFragranceResponse>> createFragrance(
            @RequestBody InsertFragranceParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        var response = fragranceService.insertFragrance(param, baseParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/fragrances/{id}")
    @Operation(summary = "Update Fragrance")
    public ResponseEntity<Void> updateFragrance(
            @PathVariable("id") long id,
            @RequestBody UpdateFragranceParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.updateFragrance(id, param, baseParam);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/fragrances/{id}")
    @Operation(summary = "Delete Fragrance")
    public ResponseEntity<Void> deleteFragrance(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.deleteFragrance(id, baseParam);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/fragrances/{id}/notes")
    @Operation(summary = "Get Fragrance Notes")
    public ResponseEntity<BaseResponse<NoteListResponse>> updateFragranceNote(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId,
            Pageable pageable) {

        var baseParam = new BaseParam(version, correlationId);

        var response = fragranceService.getFragranceNotes(id, pageable, baseParam);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fragrances/{id}/notes")
    @Operation(summary = "Insert New Fragrance Note")
    public ResponseEntity<Void> updateFragranceNote(
            @PathVariable("id") long id,
            @Valid @RequestBody InsertFragranceNoteParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.updateFragranceNote(id, param, baseParam);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/fragrances/{id}/notes/{noteId}")
    @Operation(summary = "Delete Fragrance Note")
    public ResponseEntity<Void> deleteFragranceNote(
            @PathVariable("id") long id,
            @PathVariable("noteId") long noteId,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.deleteFragranceNote(id, noteId, baseParam);
        return ResponseEntity.noContent().build();
    }
}


