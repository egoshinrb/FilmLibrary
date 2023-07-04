package com.example.filmlibrary.controller.mvc;

import com.example.filmlibrary.dto.DirectorDTO;
import com.example.filmlibrary.service.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/directors")
public class MVCDirectorController {

private final DirectorService directorService;


    public MVCDirectorController(DirectorService  directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<DirectorDTO> directors = directorService.listAll();
        model.addAttribute("directors", directors);
        return "directors/viewAllDirectors";
    }

    @GetMapping("/add")
    public String create() {
        return "directors/addDirector";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("directorForm") DirectorDTO newDirector) {
        log.info(newDirector.toString());
        directorService.create(newDirector);
        return "redirect:/directors";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("director", directorService.getOne(id));
        return "directors/updateDirector";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("directorForm") DirectorDTO directorDTO) {
        directorService.update(directorDTO);
        return "redirect:/directors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws NotFoundException {
        directorService.deleteSoft(id);
        return "redirect:/directors";
    }

    @PostMapping("/search")
    public String searchAuthors(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                @ModelAttribute("directorSearchForm") DirectorDTO directorDTO,
                                Model model) {
        if (StringUtils.hasText(directorDTO.getDirectorsFIO()) || StringUtils.hasLength(directorDTO.getDirectorsFIO())) {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "DirectorsFIO"));
            model.addAttribute("authors", directorService.searchDirectors(directorDTO.getDirectorsFIO().trim(), pageRequest));
            return "directors/viewAllDirectors";
        }
        else {
            return "redirect:/directors";
        }
    }

}
