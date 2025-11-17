package com.merufureku.aromatica.fragrance_service.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FragranceNotesId implements Serializable {

    @Column(name = "fragrance_id")
    private Long fragranceId;

    @Column(name = "note_id")
    private Long noteId;
}
