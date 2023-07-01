package com.example.filmlibrary.source.mapper;

import com.example.filmlibrary.source.DTO.FilmDTO;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.model.GenericModel;
import com.example.filmlibrary.source.repository.DirectorRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FilmMapper
        extends GenericMapper<Film, FilmDTO> {

    private final DirectorRepository directorRepository;

    public FilmMapper(ModelMapper modelMapper, DirectorRepository directorRepository) {
        super(Film.class, FilmDTO.class, modelMapper);
        this.directorRepository = directorRepository;
    }

    @PostConstruct
    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(Film.class, FilmDTO.class)
                .addMappings(mapping -> mapping.skip(FilmDTO::setDirectorsIds))
                .setPostConverter(toDTOConverter());

        //TypeMap — это правило, в котором указываем все нюансы маппинга

        modelMapper.createTypeMap(FilmDTO.class, Film.class)
                .addMappings(mapping -> mapping.skip(Film::setDirectors))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(FilmDTO source, Film destination) {
        if(!Objects.isNull(source.getDirectorsIds())) {
            destination.setDirectors(directorRepository.findAllById(source.getDirectorsIds()));
        } else {
            destination.setDirectors(Collections.emptyList());
        }
    }

    @Override
    protected List<Long> getIds(Film source) {
        return Objects.isNull(source) || Objects.isNull(source.getDirectors())
                ? null
                : source.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }

    @Override
    protected void mapSpecificFields(Film source, FilmDTO destination) {
        destination.setDirectorsIds(getIds(source));
    }
}
