package com.example.filmlibrary.constants;

import java.util.List;

public interface SecurityConstants {


    List<String> RESOURCES_WHITE_LIST = List.of(
            "/resources/**",
            "/static/**",
            "/js/**",
            "/css/**",
            "/",
            "swagger-ui/**");

    List<String> FILMS_WHITE_LIST = List.of("/films");

    final List<String> FILMS_PERMISIONS_LIST = List.of(
            "/films/add",
            "films/update",
            "films/delete");

    List<String> USER_WHITE_LIST = List.of(
            "/login",
            "/users/registration",
            "/users/remember-password/");
}
