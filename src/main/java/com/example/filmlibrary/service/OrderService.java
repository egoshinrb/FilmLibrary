package com.example.filmlibrary.service;

import com.example.filmlibrary.dto.FilmDTO;
import com.example.filmlibrary.dto.OrderDTO;
import com.example.filmlibrary.mapper.OrderMapper;
import com.example.filmlibrary.model.Film;
import com.example.filmlibrary.model.Order;
import com.example.filmlibrary.model.User;
import com.example.filmlibrary.repository.FilmRepository;
import com.example.filmlibrary.repository.OrderRepository;
import com.example.filmlibrary.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService extends GenericService<Order, OrderDTO> {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final FilmService filmService;
    protected OrderService(OrderRepository orderRepository, OrderMapper orderMapper,
                           FilmRepository filmRepository, UserRepository userRepository,
                           FilmService filmService) {
        super(orderRepository, orderMapper);
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.filmService = filmService;
    }


    public OrderDTO addUserAndFilm(Long orderId, Long userId, Long filmId) {
        OrderDTO order = getOne(orderId);
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new NotFoundException("Фильм не найден!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Посетитель не найден"));
        order.setFilmId(film.getId());
        order.setUserId(user.getId());
        update(order);
        return order;
    }


    public OrderDTO rentFilm(final OrderDTO orderDTO) {
        FilmDTO filmDTO = filmService.getOne(orderDTO.getFilmId());
        filmDTO.setAmount(filmDTO.getAmount() - 1);
        filmService.update(filmDTO);
        long rentPeriod = orderDTO.getRentPeriod() != null ? orderDTO.getRentPeriod() : 14L;
        orderDTO.setRentDate(LocalDateTime.now());
        orderDTO.setReturned(false);
        orderDTO.setRentPeriod((int) rentPeriod);
        orderDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        return mapper.toDTO(repository.save(mapper.toEntity(orderDTO)));
    }

    public Page<OrderDTO> listUserRentFilms(final Long id,
                                                   final Pageable pageRequest) {
        Page<Order> objects = ((OrderRepository) repository).getOrderByUserId(id, pageRequest);
        List<OrderDTO> results = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageRequest, objects.getTotalElements());
    }

    public void returnFilm(final Long id) {
        OrderDTO orderDTO = getOne(id);
        orderDTO.setReturned(true);
        orderDTO.setReturnDate(LocalDateTime.now());
        Long filmId = orderDTO.getFilmId().longValue();
        FilmDTO filmDTO = filmService.getOne(filmId);   ///Warn
        filmDTO.setAmount(filmDTO.getAmount() + 1);
        update(orderDTO);
        filmService.update(filmDTO);
    }


}


