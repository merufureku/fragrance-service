package com.merufureku.aromatica.fragrance_service.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "fragrance_notes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FragranceNotes {

    @EmbeddedId
    private FragranceNotesId id;
}
