package com.example.filmlibrary.source.mapper;

import com.example.filmlibrary.source.DTO.GenericDTO;
import com.example.filmlibrary.source.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);
    D toDTO(E entity);
    List<E> toEntities(List<D> dtos);
    List<D> toDTOs(List<E> entities);
}

