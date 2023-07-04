package com.example.filmlibrary.dto;

import com.example.filmlibrary.model.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO extends GenericDTO {

    private String filmTitle;
    private LocalDate premierYear;
    private String country;
    private Integer amount;
    private Genre genre;
    private List<Long> directorIds;
}


