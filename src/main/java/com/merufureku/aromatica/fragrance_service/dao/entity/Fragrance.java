package com.merufureku.aromatica.fragrance_service.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "fragrances")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fragrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "gender")
    private String gender;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "fragrance_notes",
            joinColumns = @JoinColumn(name = "fragrance_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id")
    )
    private List<Notes> notes;
}
