package com.merufureku.aromatica.fragrance_service.dao.specifications;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import org.springframework.data.jpa.domain.Specification;

public class FragranceSpecification {

    public static Specification<Fragrance> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Fragrance> hasBrand(String brand) {
        return (root, query, criteriaBuilder) ->
                brand == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Fragrance> hasGender(String gender) {
        return (root, query, criteriaBuilder) ->
                gender == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("gender")), "%" + gender.toLowerCase() + "%");
    }

    public static Specification<Fragrance> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%");
    }

    public static Specification<Fragrance> hasCountryOfOrigin(String countryOfOrigin) {
        return (root, query, criteriaBuilder) ->
                countryOfOrigin == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("countryOfOrigin")), "%" + countryOfOrigin.toLowerCase() + "%");
    }
}
