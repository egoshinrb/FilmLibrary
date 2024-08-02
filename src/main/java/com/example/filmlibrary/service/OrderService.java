package com.example.filmlibrary.service;

import com.example.filmlibrary.DTO.OrderDTO;
import com.example.filmlibrary.mapper.OrderMapper;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.model.Order;
import com.example.filmlibrary.model.User;
import com.example.filmlibrary.repository.FilmRepository;
import com.example.filmlibrary.repository.OrderRepository;
import com.example.filmlibrary.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;

@Service
@Slf4j
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
