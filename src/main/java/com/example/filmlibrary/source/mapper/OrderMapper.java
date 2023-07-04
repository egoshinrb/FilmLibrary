package com.example.filmlibrary.source.mapper;

import com.example.filmlibrary.source.DTO.OrderDTO;
import com.example.filmlibrary.source.model.Order;
import com.example.filmlibrary.source.repository.FilmRepository;
import com.example.filmlibrary.source.repository.UserRepository;
import com.example.filmlibrary.source.service.FilmService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

@Component
public class OrderMapper extends GenericMapper<Order, OrderDTO> {

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final FilmService filmService;

    protected OrderMapper(ModelMapper mapper,
                          FilmRepository filmRepository,
                          UserRepository userRepository,
                          FilmService filmService) {
        super(Order.class, OrderDTO.class, mapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.filmService = filmService;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(Order.class, OrderDTO.class)
                .addMappings(m -> m.skip(OrderDTO::setUserId))
                .addMappings(m -> m.skip(OrderDTO::setFilmId))
                .addMappings(m -> m.skip(OrderDTO::setFilmDTO))
                .setPostConverter(toDTOConverter());

        super.modelMapper.createTypeMap(OrderDTO.class, Order.class)
                .addMappings(m -> m.skip(Order::setUser))
                .addMappings(m -> m.skip(Order::setFilm))
                .addMappings(m -> m.skip(Order::setFilm))
                .setPostConverter(toEntityConverter());
    }


    @Override
    protected void mapSpecificFields(OrderDTO source, Order destination) {
        destination.setFilm(filmRepository.findById(source.getFilmId()).orElseThrow(() -> new NotFoundException("Фильм не найден")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователя не найдено")));
    }


    @Override
    protected void mapSpecificFields(Order source, OrderDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setFilmId(source.getFilm().getId());
        destination.setFilmDTO(filmService.getOne(source.getFilm().getId()));
    }

    @Override
    protected List<Long> getIds(Order entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}