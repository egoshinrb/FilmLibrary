package com.example.filmlibrary.source.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "title", nullable = false) // TODO добавить уникальность
    private RoleTitle title;

    @Column(name = "description", nullable = false)
    private String description;

}
