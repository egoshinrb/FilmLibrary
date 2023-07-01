package com.example.filmlibrary.source.service;

import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.mapper.FilmMapper;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
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
