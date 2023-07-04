package com.example.filmlibrary.service;

import com.example.filmlibrary.dto.FilmDTO;
import com.example.filmlibrary.mapper.FilmMapper;
import com.example.filmlibrary.model.Director;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.repository.DirectorRepository;
import com.example.filmlibrary.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class FilmService extends GenericService<Film, FilmDTO> {

    private final DirectorRepository directorRepository;

    public FilmService(FilmRepository repository, FilmMapper mapper, DirectorRepository directorRepository) {
        super(repository, mapper);
        this.directorRepository = directorRepository;
    }

    public FilmDTO addDirector(final Long filmId,
                               final Long directorId) {
        FilmDTO film = getOne(filmId);
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new NotFoundException("Режиссер не найден"));
        film.getDirectorIds().add(director.getId());
        update(film);
        return film;
    }


}
