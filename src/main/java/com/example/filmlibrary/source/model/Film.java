package com.example.filmlibrary.source.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "film_sequence", allocationSize = 1)
public class Film extends GenericModel {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "premier_year", nullable = false)
    private Short premierYear;

    @Column(name = "country", nullable = false)
    private String country;

    @Enumerated
    @Column(name = "film_genre", nullable = false)
    private FilmGenre genre;

    @ManyToMany
    @JoinTable(name = "film_directors",
        joinColumns = @JoinColumn(name = "film_id"), foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
        inverseJoinColumns = @JoinColumn(name = "director_id"), inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS"))
    List<Director> directors;
}
