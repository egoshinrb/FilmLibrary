package com.example.filmlibrary.controller.rest;

import com.example.filmlibrary.DTO.FilmDTO;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/films")
@Tag(name = "Фильмы", description = "Контроллер для работы с фильмами из фильмотеки")
public class FilmController
        extends GenericController<Film, FilmDTO>{

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
