package com.example.filmlibrary.source.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "director_sequence", allocationSize = 1)
public class Director extends GenericModel {
    @Column(name = "directors_fio", nullable = false)
    private String directors_fio;

    @Column(name = "position", nullable = false)
    private String position;

    @ManyToMany(mappedBy = "directors")
    List<Film> films;
}
