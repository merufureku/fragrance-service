package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.*;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceListResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.InsertFragranceResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

@RestController
public class FragranceController {

    private final IFragranceService fragranceService;

    public FragranceController(IFragranceService fragranceService) {
        this.fragranceService = fragranceService;
    }

    @GetMapping("/fragrances")
    @ManagedOperation(description = "Get list of fragrances")
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

    @GetMapping("/fragrances/{id}")
    @ManagedOperation(description = "Get Fragrance")
    public ResponseEntity<BaseResponse<FragranceDetailedResponse>> getFragrance(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = fragranceService.getFragrance(id, baseParam);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/fragrances")
    @ManagedOperation(description = "Insert Fragrance")
    public ResponseEntity<BaseResponse<InsertFragranceResponse>> createFragrance(
            @RequestBody InsertFragranceParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        var response = fragranceService.insertFragrance(param, baseParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/fragrances/{id}")
    @ManagedOperation(description = "Update Fragrance")
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
    @ManagedOperation(description = "Deleting Fragrance")
    public ResponseEntity<Void> deleteFragrance(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.deleteFragrance(id, baseParam);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/fragrances/{id}/notes")
    @ManagedOperation(description = "Update Fragrance Notes")
    public ResponseEntity<Void> updateFragranceNotes(
            @PathVariable("id") long id,
            @Valid @RequestBody InsertFragranceNoteParam param,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);

        fragranceService.updateFragranceNotes(id, param, baseParam);
        return ResponseEntity.noContent().build();
    }

}


