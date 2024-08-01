package com.example.filmlibrary.DTO;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DirectorDTO extends GenericDTO{
    private String directorsFio;
    private Double position;
    private List<Long> filmsIds;
}


