package com.merufureku.aromatica.fragrance_service.controller;

import com.merufureku.aromatica.fragrance_service.dto.params.BaseParam;
import com.merufureku.aromatica.fragrance_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceDetailedResponse;
import com.merufureku.aromatica.fragrance_service.dto.responses.FragranceListResponse;
import com.merufureku.aromatica.fragrance_service.services.interfaces.IFragranceService;
import org.springframework.data.domain.Pageable;
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
    public BaseResponse<FragranceListResponse> getFragrances(
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

        return ResponseEntity.ok(response).getBody();
    }

    @GetMapping("/fragrances/{id}")
    @ManagedOperation(description = "Get Fragrance")
    public BaseResponse<FragranceDetailedResponse> getFragrance(
            @PathVariable("id") long id,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = fragranceService.getFragrance(id, baseParam);

        return ResponseEntity.ok(response).getBody();
    }

    @PostMapping("fragrances")
    @ManagedOperation(description = "Create Fragrance")
    public String createFragrance() {
        return null;
    }

}


