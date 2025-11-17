package com.merufureku.aromatica.fragrance_service.dao.specifications;

import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;
import org.springframework.data.jpa.domain.Specification;

public class NotesSpecification {

    public static Specification<Notes> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Notes> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%");
    }
}
