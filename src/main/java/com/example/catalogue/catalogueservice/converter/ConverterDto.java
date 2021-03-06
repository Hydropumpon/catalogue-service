package com.example.catalogue.catalogueservice.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface ConverterDto<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntity(List<D> dtoList) {
        return dtoList.stream()
                      .map(this::toEntity)
                      .collect(Collectors.toList());
    }

    default List<D> toDto(List<E> entityList) {
        return entityList.stream()
                         .map(this::toDto)
                         .collect(Collectors.toList());
    }
}
