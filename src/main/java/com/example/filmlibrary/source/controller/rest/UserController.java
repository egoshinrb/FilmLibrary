package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.config.jwt.JWTTokenUtil;
import com.example.filmlibrary.source.service.GenericService;
import com.example.filmlibrary.source.service.UserService;
import com.example.filmlibrary.source.model.User;
import com.example.filmlibrary.source.DTO.LoginDTO;
import com.example.filmlibrary.source.DTO.UserDTO;
import com.example.filmlibrary.source.service.userdetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/rest/users")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями фильмотеки")

public class UserController extends GenericController<User, UserDTO> {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final UserService userService;

    public UserController(GenericService<User, UserDTO> genericService,
                          CustomUserDetailsService customUserDetailsService,
                          JWTTokenUtil jwtTokenUtil,
                          UserService userService) {
        super(genericService);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDTO.getLogin());
        log.info("foundUser: {}", foundUser);
        if (!userService.checkPassword(loginDTO.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации! \n Неверный пароль...");
        }
        String token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("role", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }
}