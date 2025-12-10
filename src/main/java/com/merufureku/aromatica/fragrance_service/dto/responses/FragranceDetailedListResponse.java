package com.merufureku.aromatica.fragrance_service.dto.responses;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;

import java.util.List;

public record FragranceDetailedListResponse(List<FragranceDetailedResponse> fragrances) {

    public record FragranceDetailedResponse(
            Long fragranceId,
            String name,
            String brand,
            String description,
            List<NoteResponse> noteResponse){

        public FragranceDetailedResponse(Fragrance fragrance){
            this(
                    fragrance.getId(),
                    fragrance.getName(),
                    fragrance.getBrand(),
                    fragrance.getDescription(),
                    fragrance.getNotes().stream()
                            .map(NoteResponse::new)
                            .toList()
            );
        }
    }
}