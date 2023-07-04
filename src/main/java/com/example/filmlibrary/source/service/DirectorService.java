package com.example.filmlibrary.source.service;

import com.example.filmlibrary.source.DTO.DirectorDTO;
import com.example.filmlibrary.source.mapper.DirectorMapper;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.repository.DirectorRepository;
import com.example.filmlibrary.source.repository.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorService extends GenericService<Director, DirectorDTO> {

    private final FilmRepository filmRepository;


    public DirectorService(DirectorRepository repository,
                           DirectorMapper directorMapper,
                           FilmRepository filmRepository) {
        super(repository, directorMapper);
        this.filmRepository = filmRepository;
    }


    public List<DirectorDTO> getOne(final List<Long> idList) {
        List<DirectorDTO> resultList = new ArrayList<>();
        for (Long aLong : idList) {
            resultList.add(mapper.toDTO(repository.findById(aLong).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + aLong + " не найдено!"))));
        }
        return resultList;
    }

    public DirectorDTO addFilm(Long filmId, Long directorId) {

        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("Фильм не найден!"));

        DirectorDTO director = getOne(directorId);
        director.getFilmIds().add(film.getId());
        update(director);
        return director;
    }

    public void deleteSoft(final Long id) throws NotFoundException {
        Director directorToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("Объект не найден"));
        directorToDelete.setDeleted(true);
        repository.save(directorToDelete);
    }

    public Page<DirectorDTO> searchDirectors(final String fio,
                                             Pageable pageable) {
        Page<Director> directors = ((DirectorRepository)repository).findAllByDirectorsFIOContainsIgnoreCaseAndDeletedIsFalse(fio, pageable);
        List<DirectorDTO> result = mapper.toDTOs(directors.getContent());
        return new PageImpl<>(result, pageable, directors.getTotalElements());
    }


}
