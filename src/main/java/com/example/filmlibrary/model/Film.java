package com.example.filmlibrary.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "film_sequence", allocationSize = 1)
public class Film extends GenericModel {

    @Column(name = "title", nullable = false)
    private String filmTitle;

    @Column(name = "premier_year",  nullable = false)
    private LocalDate premierYear;

    @Column(name = "country")
    private String country;

    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToMany
    @JoinTable(name = "film_directors",
            joinColumns = @JoinColumn(name = "film_id"), foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORSS"),
            inverseJoinColumns = @JoinColumn(name = "director_id"), inverseForeignKey = @ForeignKey(name ="FK_DIRECTORS_FILMS"))
    private List<Director> directors;

}