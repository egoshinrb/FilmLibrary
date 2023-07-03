package com.example.filmlibrary.source.controller.rest;

import com.example.filmlibrary.source.DTO.GenericDTO;
import com.example.filmlibrary.source.model.Director;
import com.example.filmlibrary.source.model.GenericModel;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@Slf4j //логгер
public abstract class GenericController<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericController(GenericService<E, D> service) {
        this.service = service;
    }

    @Operation(description = "Получить запись по ID", method = "getOneById")
    @RequestMapping(value = "/getOneById",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping -вместо method
    public ResponseEntity<D> getOneById(@RequestParam(value = "id") long id) {
// http://localhost:8080/directors/getOneById?id=1
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getOne(id));
    }

    @Operation(description = "Получить все записи", method = "getAll")
    @RequestMapping(value = "/getAll",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.listAll());
    }

    @Operation(description = "Создать запись", method = "add")
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE) // consumes == newEntity
    public ResponseEntity<D> create(@RequestBody D newEntity) {
        log.info(newEntity.toString());
        newEntity.setCreatedWhen(LocalDateTime.now());
        log.info(newEntity.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(newEntity));
    }

    @Operation(description = "Обновить запись", method = "update")
    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(@RequestBody D updatedEntity,
                                    @RequestParam(value = "id") Long id) {
        updatedEntity.setId(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(updatedEntity));
    }

    //localhost:8080/authors/delete?id=1 - @RequestParam
    //localhost:8080/authors/delete/1 - @PathVariable
    @Operation(description = "Удалить запись", method = "delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}