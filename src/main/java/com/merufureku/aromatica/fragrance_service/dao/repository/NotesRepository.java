package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long>, JpaSpecificationExecutor<Notes> {

    boolean existsByName(String name);

}
