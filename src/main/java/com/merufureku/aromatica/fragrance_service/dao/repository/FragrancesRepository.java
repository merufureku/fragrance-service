package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.Fragrance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FragrancesRepository extends JpaRepository<Fragrance, Long>, JpaSpecificationExecutor<Fragrance> {

    @EntityGraph(attributePaths = {"notes"})
    @Query("SELECT f FROM Fragrance f WHERE f.id = :id")
    Optional<Fragrance> findByIdWithNotes(@Param("id") Long id);

    @EntityGraph(attributePaths = {"notes"})
    @Query("SELECT f FROM Fragrance f")
    List<Fragrance> findAllWithNotes();

    @EntityGraph(attributePaths = {"notes"})
    @Query("SELECT f FROM Fragrance f WHERE f.id IN (:ids)")
    List<Fragrance> findAllByIdWithNotes(@Param("ids") Set<Long> ids);

    @EntityGraph(attributePaths = {"notes"})
    @Query("SELECT f FROM Fragrance f WHERE f.id NOT IN (:ids)")
    List<Fragrance> findAllByIdNotInWithNotes(@Param("ids") Set<Long> ids);

    boolean existsByNameAndBrand(String name, String brand);
}
