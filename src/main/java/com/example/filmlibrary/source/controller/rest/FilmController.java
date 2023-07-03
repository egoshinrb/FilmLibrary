package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.service.FilmService;
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
@RequestMapping(value = "/api/rest/films")
@Tag(name = "Фильмы", description = "Контроллер для работы с фильмами из фильмотеки")
public class FilmController extends GenericController<Film, FilmDTO>{
    public FilmController(FilmService filmService) {
        super(filmService);
    }


    @Operation(description = "Добавить режиссера к фильму")
    @RequestMapping(value = "/addDirector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> addFilm(@RequestParam(value = "filmId") Long filmId,
                                        @RequestParam(value = "directorId") Long directorId) {

        return ResponseEntity.status(HttpStatus.OK).body(((FilmService) service).addFilm(filmId, directorId));
    }
}