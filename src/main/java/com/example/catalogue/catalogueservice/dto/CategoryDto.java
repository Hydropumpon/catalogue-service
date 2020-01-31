package com.example.catalogue.catalogueservice.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

@Getter
@Builder
public class CategoryDto {

    private final Integer id;

    @NotNull
    private final String name;

    @ConstructorProperties({"id", "name"})
    public CategoryDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
