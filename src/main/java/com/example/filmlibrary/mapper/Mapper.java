package com.example.filmlibrary.mapper;

import com.example.filmlibrary.dto.GenericDTO;
import com.example.filmlibrary.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}
