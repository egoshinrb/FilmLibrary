package com.example.filmlibrary.source.service;

import com.example.filmlibrary.source.DTO.DirectorDTO;
import com.example.filmlibrary.source.mapper.DirectorMapper;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
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
