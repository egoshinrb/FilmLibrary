package com.example.filmlibrary.source.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI filmLibraryProject() {
        return new OpenAPI()
                .info(new Info()
                        .title("Онлайн Библиотека")
                        .description("Сервис, позволяющий получить доступ к фильму")
                        .version("0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Roman Egoshin").email("roman_sinor@mail.ru").url(""))
                );
    }
}
