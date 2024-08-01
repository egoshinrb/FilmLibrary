package com.example.filmlibrary.service;

import com.example.filmlibrary.DTO.FilmDTO;
import com.example.filmlibrary.mapper.FilmMapper;
import com.example.filmlibrary.model.Director;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.repository.DirectorRepository;
import com.example.filmlibrary.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
public class FilmService
        extends GenericService<Film, FilmDTO> {

    DirectorRepository directorRepository;

    public FilmService(FilmRepository repository, FilmMapper mapper, DirectorRepository directorRepository) {
        super(repository, mapper);
        this.directorRepository = directorRepository;
    }

    public FilmDTO addFilm(Long filmId, Long directorId) {
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new NotFoundException("режиссер не найден"));
        FilmDTO film = getOne(filmId);
        film.getDirectorsIds().add(director.getId());
        update(film);
        return film;
    }
}

