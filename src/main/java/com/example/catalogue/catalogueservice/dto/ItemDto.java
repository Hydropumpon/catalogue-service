package com.example.catalogue.catalogueservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class ItemDto {

    private final Integer id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final Integer manufacturerId;

    @ConstructorProperties({"id", "name", "description", "price", "manufacturerId"})
    public ItemDto(Integer id, @NotNull String name, @NotNull String description,
                   @NotNull BigDecimal price, @NotNull Integer manufacturerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.manufacturerId = manufacturerId;
    }
}
