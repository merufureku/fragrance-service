package com.merufureku.aromatica.fragrance_service.dao.repository;

import com.merufureku.aromatica.fragrance_service.dao.entity.FragranceNotes;
import com.merufureku.aromatica.fragrance_service.dao.entity.FragranceNotesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragranceNotesRepository extends JpaRepository<FragranceNotes, FragranceNotesId> {}
