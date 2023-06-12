package com.example.filmlibrary.source.controller;

import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями фильмотеки")
public class UserController extends GenericController<User> {
    public UserController(UserRepository userRepository) {
        super(userRepository);
    }
}

