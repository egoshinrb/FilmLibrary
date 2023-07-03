package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.DTO.DirectorDTO;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

//@Hidden // swagger annotation скрыть контроллер
@RestController
@RequestMapping("/api/rest/directors") // http://localhost:8080/directors
@Tag(name = "Режиссеры", description = "Контроллер для работы с режиссерами фильмов из фильмотеки")
public class DirectorController extends GenericController<Director, DirectorDTO> {

    public DirectorController(DirectorService directorService) {
        super(directorService);
    }


    @Operation(description = "Добавить фильм к режиссеру")
    @RequestMapping(value = "/addFilm",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDTO> addFilm(@RequestParam(value = "filmId") Long filmId,
                                            @RequestParam(value = "directorId") Long directorId) {
        return ResponseEntity.status(HttpStatus.OK).body(((DirectorService) service).addFilm(filmId, directorId));
    }
}
