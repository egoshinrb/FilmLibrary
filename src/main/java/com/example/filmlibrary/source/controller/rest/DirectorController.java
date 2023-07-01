package com.example.filmlibrary.source.controller.rest;

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
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

//@Hidden // swagger annotation скрыть контроллер
@RestController
@RequestMapping("/directors") // http://localhost:8080/directors
@Tag(name = "Режиссеры", description = "Контроллер для работы с режиссерами фильмов из фильмотеки")
public class DirectorController extends GenericController<Director> {

    private final DirectorRepository directorRepository;

    private final FilmRepository filmRepository;

    public DirectorController(GenericRepository<Director> genericRepository, DirectorRepository directorRepository, FilmRepository filmRepository) {
        super(genericRepository);
        this.directorRepository = directorRepository;
        this.filmRepository = filmRepository;
    }

    @Operation(description = "Добавить фильм к режиссеру")
    @RequestMapping(value = "/addFilm",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Director> addFilm(@RequestParam(value = "filmId") Long filmId,
                                            @RequestParam(value = "directorId") Long directorId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("фильм не найден"));
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new NotFoundException("режиссер не найден"));

        director.getFilms().add(film);

        return ResponseEntity.status(HttpStatus.OK).body(directorRepository.save(director));
    }
}
