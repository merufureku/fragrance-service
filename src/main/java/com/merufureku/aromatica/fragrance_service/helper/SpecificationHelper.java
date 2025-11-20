package com.merufureku.aromatica.fragrance_service.helper;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;
import com.merufureku.aromatica.fragrance_service.dao.specifications.FragranceSpecification;
import com.merufureku.aromatica.fragrance_service.dao.specifications.NotesSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationHelper {

    public Specification<Notes> buildNotesSpecification(String name, String type) {
        return Specification
                .allOf(NotesSpecification.hasName(name))
                .and(NotesSpecification.hasType(type)
                );
    }

    public Specification<Fragrance> buildFragranceSpecification(String name, String brand, String gender,
                                                                String type, String countryOfOrigin) {
        return Specification
                .allOf(FragranceSpecification.hasName(name))
                    .and(FragranceSpecification.hasBrand(brand))
                    .and(FragranceSpecification.hasGender(gender))
                    .and(FragranceSpecification.hasType(type))
                    .and(FragranceSpecification.hasCountryOfOrigin(countryOfOrigin)
                );
    }
}
