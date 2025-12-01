package com.merufureku.aromatica.fragrance_service.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collections")
public class Collections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fragrance_id", nullable = false)
    private Long fragranceId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Fragrance fragrance;
}
