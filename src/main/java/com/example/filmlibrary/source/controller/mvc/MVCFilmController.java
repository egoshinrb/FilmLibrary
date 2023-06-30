package com.example.filmlibrary.source.controller.mvc;

import com.example.filmlibrary.source.DTO.DirectorDTO;
import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.service.DirectorService;
import com.example.filmlibrary.source.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/films")
public class MVCFilmController {

    private final FilmService filmService;
    private final DirectorService directorService;

    public MVCFilmController(FilmService filmService, DirectorService directorService) {
        this.filmService = filmService;
        this.directorService = directorService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<FilmDTO> films = filmService.listAll();
        model.addAttribute("films", films);

        Map<FilmDTO, List<String>> filmsAndDirectors = new HashMap<>();

        for (FilmDTO film : films) {
            List<Long> directorsId = filmService.getOne(film.getId()).getDirectorsIds();
            List<String> directors = new ArrayList<>();
            for (Long id : directorsId) {
                directors.add(directorService.getOne(id).getDirectorsFio());
            }
            filmsAndDirectors.put(film, directors);
        }

        model.addAttribute("filmsAndDirectors", filmsAndDirectors);

        return "films/viewAllFilms";
    }

//    @GetMapping("/add")
//    public String create() {
//        return "films/addFilm";
//    }

    @GetMapping("/add")
    public String create(Model model) {
        List<DirectorDTO> directors = directorService.listAll();
        model.addAttribute("directors", directors);
        return "films/addFilm";
    }


    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDTO newFilm) {
        log.info(newFilm.toString());
        filmService.create(newFilm);
        return "redirect:/films";
    }

    @GetMapping("/addDirector")
    public String update() {
        return "films/addDirectorToFilm";
    }

    @PostMapping("/addDirector")
    public String update(@RequestParam(name = "filmId") Long filmId, @RequestParam(name = "directorId") Long directorId) {
        filmService.addFilm(filmId, directorId);
        return "redirect:/films";
    }
}