package com.example.filmlibrary.service;

import com.example.filmlibrary.dto.DirectorDTO;
import com.example.filmlibrary.model.Director;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public interface DirectorTestData {
    DirectorDTO DIRECTOR_DTO_1 = new DirectorDTO("directorFio1",
            "position1",
            new ArrayList<>());

    DirectorDTO DIRECTOR_DTO_2 = new DirectorDTO("directorFio2",
            "position2",
            new ArrayList<>());


    DirectorDTO DIRECTOR_DTO_3_DELETED = new DirectorDTO("directorFio3",
            "position3",
            new ArrayList<>());


    List<DirectorDTO> DIRECTOR_DTO_LIST = Arrays.asList(DIRECTOR_DTO_1, DIRECTOR_DTO_2, DIRECTOR_DTO_3_DELETED);


    Director DIRECTOR_1 = new Director("director1",
            "position1",
            false,
            null);

    Director DIRECTOR_2 = new Director("director2",
            "position2",
            false,
            null);

    Director DIRECTOR_3 = new Director("director3",
            "position3",
            false,
            null);

    List<Director> DIRECTOR_LIST = Arrays.asList(DIRECTOR_1, DIRECTOR_2, DIRECTOR_3);
}

