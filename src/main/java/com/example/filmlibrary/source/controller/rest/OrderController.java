package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.model.Order;
import com.example.filmlibrary.source.repository.GenericRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/info")
@Tag(name = "Прокат фильмов",
        description = "Контроллер для работы с прокатом фильмов пользователям фильмотеки")
public class OrderController extends GenericController<Order> {
    public OrderController(GenericRepository<Order> genericRepository) {
        super(genericRepository);
    }
}