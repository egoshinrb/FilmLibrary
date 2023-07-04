package com.example.filmlibrary.service;


import com.example.filmlibrary.source.DTO.GenericDTO;
import com.example.filmlibrary.source.mapper.GenericMapper;
import com.example.filmlibrary.source.model.GenericModel;
import com.example.filmlibrary.source.repository.GenericRepository;
import com.example.filmlibrary.source.service.GenericService;
import com.example.filmlibrary.source.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.builder()
                        .username("USER"),
                null,
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected abstract void getAll();
    protected abstract void getOne();
    protected abstract void create();
    protected abstract void update();
    protected abstract void getAllNotDeleted();

}


