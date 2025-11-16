package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FragrancesRepository extends JpaRepository<Fragrance, Long>, JpaSpecificationExecutor<Fragrance> {

    boolean existsByNameAndBrand(String name, String brand);

}
