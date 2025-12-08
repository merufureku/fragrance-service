package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FragrancesRepository extends JpaRepository<Fragrance, Long>, JpaSpecificationExecutor<Fragrance> {

    boolean existsByNameAndBrand(String name, String brand);

    List<Fragrance> findAllByIdNotIn(Set<Long> ids);

}
