package com.example.filmlibrary.controller.mvc;

import com.example.filmlibrary.dto.DirectorDTO;
import com.example.filmlibrary.dto.FilmDTO;
import com.example.filmlibrary.service.DirectorService;
import com.example.filmlibrary.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return "films/viewAllFilms";
    }

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
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("film", filmService.getOne(id));
        FilmDTO film = filmService.getOne(id);
        model.addAttribute("directors",directorService.getOne(film.getDirectorIds()));
        return "films/viewFilm";
    }


}
