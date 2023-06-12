package com.example.filmlibrary.source.controller;

import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import com.example.filmlibrary.source.repository.GenericRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequestMapping(value = "/films")
@Tag(name = "Фильмы", description = "Контроллер для работы с фильмами из фильмотеки")
public class FilmController extends GenericController<Film>{
    private final DirectorRepository directorRepository;
    private final FilmRepository filmRepository;

    public FilmController(GenericRepository<Film> genericRepository, DirectorRepository directorRepository, FilmRepository filmRepository) {
        super(genericRepository);
        this.directorRepository = directorRepository;
        this.filmRepository = filmRepository;
    }

    @Operation(description = "Добавить режиссера к фильму")
    @RequestMapping(value = "/addDirector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Film> addFilm(@RequestParam(value = "filmId") Long filmId,
                                        @RequestParam(value = "directorId") Long directorId) {

        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("фильм не найден"));
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new NotFoundException("режиссер не найден"));

        film.getDirectors().add(director);
        return ResponseEntity.status(HttpStatus.OK).body(filmRepository.save(film));
    }
}


