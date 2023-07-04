package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.DTO.OrderDTO;
import com.example.filmlibrary.source.model.Order;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/orders/info")
@Tag(name = "Заказ фильмов",
        description = "Контроллер для работы с заказом фильмов пользователями фильмотеки")
public class OrderController extends GenericController<Order, OrderDTO> {

    public OrderController(OrderService orderService) {
        super(orderService);
    }

    @Operation(description = "Взять фильм в аренду/купить")
    @RequestMapping(value = "/addUserAndFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> addUserAndFilm(@RequestParam(value = "orderId") Long orderId,
                                                   @RequestParam(value = "userId") Long userId,
                                                   @RequestParam(value = "filmId") Long filmId) {

        return ResponseEntity.status(HttpStatus.OK).body(((OrderService) service).addUserAndFilm(orderId, userId, filmId));

    }


}
