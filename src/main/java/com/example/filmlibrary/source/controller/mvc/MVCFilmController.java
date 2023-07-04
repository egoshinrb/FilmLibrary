package com.example.filmlibrary.source.controller.mvc;

import com.example.filmlibrary.source.DTO.DirectorDTO;
import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.model.Genre;
import com.example.filmlibrary.source.service.DirectorService;
import com.example.filmlibrary.source.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
                directors.add(directorService.getOne(id).getDirectorsFIO());
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
        final List<DirectorDTO> directors = directorService.listAll()
                .stream().sorted(Comparator.comparing(DirectorDTO::getDirectorsFIO))
                .collect(Collectors.toList());
        final List<Genre> genres = Arrays.stream(Genre.values()).sorted(Comparator.comparing(Genre::getGenreTextDisplay)).toList();
        model.addAttribute("directors", directors);
        model.addAttribute("genres", genres);
        return "films/addFilm";
    }


    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDTO newFilm) {
        log.info(newFilm.toString());
        filmService.create(newFilm);
        return "redirect:/films";
    }

//    @GetMapping("/addDirector")
//    public String update() {
//        return "films/addDirectorToFilm";
//    }

        @GetMapping("/addDirector")
    public String update(Model model) {
        final List<FilmDTO> films = filmService.listAll()
                .stream().sorted(Comparator.comparing(FilmDTO::getFilmTitle))
                .toList();
        final List<DirectorDTO> directors = directorService.listAll()
                .stream().sorted(Comparator.comparing(DirectorDTO::getDirectorsFIO))
                .toList();
        model.addAttribute("films", films);
        model.addAttribute("directors", directors);

        return "films/addDirectorToFilm";
    }

    @PostMapping("/addDirector")
    public String update(@RequestParam(name = "filmId") Long filmId, @RequestParam(name = "directorId") Long directorId) {
        log.info("filmID = {} <<>> directorId = {}", filmId, directorId);
        final FilmDTO film = filmService.addFilm(filmId, directorId);
        log.info("Добавлен к фильму: \n {}",film.toString());

        return "redirect:/films";
    }
}