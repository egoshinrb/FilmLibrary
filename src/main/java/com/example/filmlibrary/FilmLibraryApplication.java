package com.example.filmlibrary;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmLibraryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FilmLibraryApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Swagger path: http://localhost:8080/swagger-ui/index.html");
    }


}
