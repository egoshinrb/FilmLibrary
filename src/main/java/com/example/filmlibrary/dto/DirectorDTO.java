package com.example.filmlibrary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class DirectorDTO extends GenericDTO {

    private String directorsFIO;
    private String position;
    private List<Long> filmIds;
    private boolean isDeleted;

    public DirectorDTO(String directorsFIO, String position, List<Long> filmIds) {
        this.directorsFIO = directorsFIO;
        this.position = position;
        this.filmIds = filmIds;
        this.isDeleted = false;
    }
}
