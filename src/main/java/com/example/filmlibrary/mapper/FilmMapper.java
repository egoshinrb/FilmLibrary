package com.example.filmlibrary.mapper;

import com.example.filmlibrary.dto.FilmDTO;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.repository.DirectorRepository;
import com.example.filmlibrary.model.GenericModel;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FilmMapper extends GenericMapper<Film, FilmDTO> {

    private final DirectorRepository directorRepository;

    protected FilmMapper(ModelMapper mapper, DirectorRepository directorRepository) {
        super(Film.class, FilmDTO.class, mapper);
        this.directorRepository = directorRepository;
    }


    @Override
    protected void mapSpecificFields(FilmDTO source, Film destination) {
        if (!Objects.isNull(source.getDirectorIds())) {
            destination.setDirectors(directorRepository.findAllById(source.getDirectorIds()));
        } else {
            destination.setDirectors(Collections.emptyList());
        }
    }


    @Override
    protected void mapSpecificFields(Film source, FilmDTO destination) {
        destination.setDirectorIds(getIds(source));
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Film.class, FilmDTO.class)
                .addMappings(m -> m.skip(FilmDTO::setDirectorIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(FilmDTO.class, Film.class)
                .addMappings(m -> m.skip(Film::setDirectors)).setPostConverter(toEntityConverter());
    }

    @Override
    protected List<Long> getIds(Film film) {
        return Objects.isNull(film) || Objects.isNull(film.getDirectors())
                ? null
                : film.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }


}
