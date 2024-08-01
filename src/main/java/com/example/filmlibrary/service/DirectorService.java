package com.example.filmlibrary.service;

import com.example.filmlibrary.DTO.DirectorDTO;
import com.example.filmlibrary.mapper.DirectorMapper;
import com.example.filmlibrary.model.Director;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.repository.DirectorRepository;
import com.example.filmlibrary.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
public class DirectorService
        extends GenericService<Director, DirectorDTO>{

    public final FilmRepository filmRepository;

    public DirectorService(DirectorRepository repository, DirectorMapper mapper, FilmRepository filmRepository) {
        super(repository, mapper);
        this.filmRepository = filmRepository;
    }

    public DirectorDTO addFilm (Long filmId, Long directorId){
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("фильм не найден"));
        DirectorDTO director = getOne(directorId);
        director.getFilmsIds().add(film.getId());
        update(director);
        return director;
    }

}