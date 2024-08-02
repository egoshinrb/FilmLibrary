package com.example.filmlibrary.mapper;

import com.example.filmlibrary.DTO.DirectorDTO;
import com.example.filmlibrary.model.Director;
import com.example.filmlibrary.model.GenericModel;
import com.example.filmlibrary.repository.FilmRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DirectorMapper
        extends GenericMapper<Director, DirectorDTO> {

    private final FilmRepository filmRepository;

    public DirectorMapper(ModelMapper modelMapper, FilmRepository filmRepository) {
        super(Director.class, DirectorDTO.class, modelMapper);
        this.filmRepository = filmRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Director.class, DirectorDTO.class)
                .addMappings(mapping -> mapping.skip(DirectorDTO::setFilmsIds))
                .setPostConverter(toDTOConverter());

        //TypeMap — это правило, в котором указываем все нюансы маппинга

        modelMapper.createTypeMap(DirectorDTO.class, Director.class)
                .addMappings(mapping -> mapping.skip(Director::setFilms))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(DirectorDTO source, Director destination) {
        if(!Objects.isNull(source.getFilmsIds())) {
            destination.setFilms(filmRepository.findAllById(source.getFilmsIds()));
        } else {
            destination.setFilms(Collections.emptyList());
        }
    }

    @Override
    protected List<Long> getIds(Director source) {
        return Objects.isNull(source) || Objects.isNull(source.getFilms())
                ? Collections.emptyList()
                : source.getFilms().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toList());
    }


    @Override
    protected void mapSpecificFields(Director source, DirectorDTO destination) {
        destination.setFilmsIds(getIds(source));
    }
}