package com.example.filmlibrary.source.DTO;

import com.example.filmlibrary.source.model.Genre;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FilmDTO extends GenericDTO{
    private String FilmTitle;
    private Integer premierYear;
    private String country;
    private Integer amount;
    private Genre genre;
    private List<Long> directorsIds;
}