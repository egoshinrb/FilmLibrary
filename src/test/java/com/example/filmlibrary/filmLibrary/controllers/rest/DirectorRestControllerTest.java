package com.example.filmlibrary.filmLibrary.controllers.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.filmlibrary.DTO.DirectorDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Profile("dev")
public class DirectorRestControllerTest
      extends CommonTestREST {
    private static Long createdTestDirectorId;
    
    @Test
    @Order(0)
    @Override
    protected void listAll() throws Exception {
        log.info("Тест по просмотру всех режиссеров через REST начат");
        String result = mvc.perform(
                    MockMvcRequestBuilders.get("/api/rest/directors/getAll")
                          .headers(super.headers)
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
              .andReturn()
              .getResponse()
              .getContentAsString();
        List<DirectorDTO> directorDTOS = objectMapper.readValue(result, new TypeReference<List<DirectorDTO>>() {});
        directorDTOS.forEach(a -> log.info(a.toString()));
        log.info("Тест по просмотру всех режиссеров через REST закончен");
    }
    
    @Test
    @Order(1)
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию режиссера через REST начат");
        //Создаем нового автора через REST-контроллер
        DirectorDTO directorDTO = new DirectorDTO("REST_TestDirectorFIO", 2., new ArrayList<>());
        
        /*
        Вызываем метод создания (POST) в контроллере, передаем ссылку на REST API в MOCK.
        В headers передаем токен для авторизации (под админом, смотри родительский класс).
        Ожидаем, что статус ответа будет успешным и что в ответе есть поле ID, а далее возвращаем контент как строку
        Все это мы конвертируем в DirectorDTO при помощи ObjectMapper от библиотеки Jackson.
        Присваиваем в статическое поле ID созданного автора, чтобы далее с ним работать.
         */
        DirectorDTO result = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.post("/api/rest/directors/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .content(asJsonString(directorDTO))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                DirectorDTO.class);
        createdTestDirectorId = result.getId();
        log.info("{}", createdTestDirectorId);
        log.info("Тест по созданию режиссера через REST закончен");
    }
    
    @Test
    @Order(2)
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению режиссера через REST начат");
        DirectorDTO existingTestDirector = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.get("/api/rest/directors/getOneById")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .param("id", String.valueOf(createdTestDirectorId))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                DirectorDTO.class);
        //обновляем поля
        existingTestDirector.setDirectorsFio("REST_TestDirectorFIO_UPDATED");
        existingTestDirector.setPosition(3.);
        
        //вызываем update по REST API
        mvc.perform(
                    MockMvcRequestBuilders.put("/api/rest/directors/update")
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .content(asJsonString(existingTestDirector))
                          .param("id", String.valueOf(createdTestDirectorId))
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Тест по обновлению режиссера через REST закончен");
    }
    
    @Test
    @Order(4)
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по удалению режиссера через REST начат");
        mvc.perform(
                    MockMvcRequestBuilders.delete("/api/rest/directors/delete/{id}", createdTestDirectorId)
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());

        DirectorDTO existingTestDirector = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.get("/api/rest/Directors/getOneById")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .param("id", String.valueOf(createdTestDirectorId))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                DirectorDTO.class);
        assertTrue(existingTestDirector.isDeleted());
        log.info("Тест по удалению режиссера через REST закончен");
        mvc.perform(
                    MockMvcRequestBuilders.delete("/api/rest/directors/delete/hard/{id}", createdTestDirectorId)
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Данные очищены!");
    }
}
