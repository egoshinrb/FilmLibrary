package com.example.filmlibrary.controller.mvc;

import com.example.filmlibrary.DTO.DirectorDTO;
import com.example.filmlibrary.service.DirectorService;
import com.example.filmlibrary.service.FilmService;
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
@RequestMapping("/directors")
public class MVCDirectorController {

    private final FilmService filmService;
    private final DirectorService directorService;

    public MVCDirectorController(FilmService filmService, DirectorService directorService) {
        this.filmService = filmService;
        this.directorService = directorService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<DirectorDTO> directors = directorService.listAll();
        model.addAttribute("directors", directors);

        Map<DirectorDTO, List<String>> DirectorsAndFilms = new HashMap<>();

        for (DirectorDTO director : directors) {
            List<Long> filmsId = directorService.getOne(director.getId()).getFilmsIds();
            List<String> films = new ArrayList<>();
            for (Long id : filmsId) {
                films.add(filmService.getOne(id).getFilmTitle());
            }
            DirectorsAndFilms.put(director, films);
        }

        model.addAttribute("DirectorsAndFilms", DirectorsAndFilms);

        return "directors/viewAllDirectors";
    }

    @GetMapping("/add")
    public String create() {
        return "directors/addDirector";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("directorsForm") DirectorDTO newDirector) {
        log.info(newDirector.toString());
        directorService.create(newDirector);
        return "redirect:/directors";
    }

    @GetMapping("/addFilm")
    public String update() {
        return "directors/addFilmToDirector";
    }

    @PostMapping("/addFilm")
    public String update(@RequestParam(name = "filmId") Long filmId, @RequestParam(name = "directorId") Long directorId) {
        directorService.addFilm(filmId, directorId);
        return "redirect:/directors";
    }
}
