package com.example.filmlibrary.source.service;

import com.example.filmlibrary.source.DTO.OrderDTO;
import com.example.filmlibrary.source.mapper.OrderMapper;
import com.example.filmlibrary.source.model.Film;
import com.example.filmlibrary.source.model.Order;
import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.repository.FilmRepository;
import com.example.filmlibrary.source.repository.OrderRepository;
import com.example.filmlibrary.source.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;

@Service
public class OrderService
        extends GenericService<Order, OrderDTO>{

    UserRepository userRepository;
    FilmRepository filmRepository;

    protected OrderService(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, FilmRepository filmRepository) {
        super(orderRepository, orderMapper);
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public OrderDTO create(OrderDTO newObject){
        User user = userRepository.findById(newObject.getUserId()).orElseThrow(()  -> new NotFoundException("пользователь не найден"));
        Film film = filmRepository.findById(newObject.getFilmId()).orElseThrow(()  -> new NotFoundException("фильм не найден"));
        newObject.setCreatedWhen(LocalDateTime.now());
        return mapper.toDTO(repository.save(mapper.toEntity(newObject)));
    }
}
