package com.example.filmlibrary.controller.rest;

import com.example.filmlibrary.DTO.OrderDTO;
import com.example.filmlibrary.model.Order;
import com.example.filmlibrary.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/info")
@Tag(name = "Прокат фильмов",
        description = "Контроллер для работы с прокатом фильмов пользователям фильмотеки")
public class OrderController extends GenericController<Order, OrderDTO> {
    public OrderController(OrderService orderService) {
        super(orderService);
    }

    @Override
    @Operation(description = "Создать запись", method = "add")
    @RequestMapping(value = "/addOrder",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE) //consumes == newEntity
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(newEntity));
    }
}