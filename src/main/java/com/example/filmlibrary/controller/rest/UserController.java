package com.example.filmlibrary.controller.rest;

import com.example.filmlibrary.DTO.FilmDTO;
import com.example.filmlibrary.DTO.UserDTO;
import com.example.filmlibrary.model.User;
import com.example.filmlibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями фильмотеки")
public class UserController extends GenericController<User, UserDTO> {

    public UserController(UserService userService) {
        super(userService);
    }

    @Operation(description = "Получить список все арендованных фильмов пользователя", method = "getAllfilmsByOneUser")
    @RequestMapping(value = "/getAllfilmsByOneUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FilmDTO>> getAllFilms(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(((UserService) service).getAllfilmsByUserId(userId));
    }
}