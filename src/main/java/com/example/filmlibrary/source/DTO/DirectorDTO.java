package com.example.filmlibrary.source.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DirectorDTO extends GenericDTO{
    private String directorsFIO;
    private Double position;
    private List<Long> filmIds;
    private boolean isDeleted;

    public DirectorDTO(String directorsFIO, Double position, List<Long> filmIds) {
        this.directorsFIO = directorsFIO;
        this.position = position;
        this.filmIds = filmIds;
        this.isDeleted = false;
    }
}