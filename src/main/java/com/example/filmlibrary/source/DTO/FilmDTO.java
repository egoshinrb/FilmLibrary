package com.example.filmlibrary.source.DTO;

import com.example.filmlibrary.source.model.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilmDTO extends GenericDTO{
    private String FilmTitle;
    private Integer premierYear;
    private String country;
    private Genre genre;
    private List<Long> directorsIds;
}