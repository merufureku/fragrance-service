package com.merufureku.aromatica.fragrance_service.dto.responses;

import java.util.List;
import org.springframework.data.domain.Page;


public record FragranceListResponse (List<FragranceResponse> fragranceResponseList, int page, int size,
                                     long totalElement, int totalPage, boolean last){

    public FragranceListResponse(List<FragranceResponse> fragranceResponses, Page fragrancePage){
        this(fragranceResponses,
             fragrancePage.getNumber(),
             fragrancePage.getSize(),
             fragrancePage.getTotalElements(),
             fragrancePage.getTotalPages(),
             fragrancePage.isLast());
    }
}
