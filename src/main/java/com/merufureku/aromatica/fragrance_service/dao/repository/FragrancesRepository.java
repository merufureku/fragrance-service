package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FragrancesRepository extends JpaRepository<Fragrances, Long>, JpaSpecificationExecutor<Fragrances> {
}
