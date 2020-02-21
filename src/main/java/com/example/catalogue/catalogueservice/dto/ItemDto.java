package com.example.catalogue.catalogueservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ItemDto {

    private final Integer id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @NotNull
    private final BigDecimal price;

    @NotNull
    @PositiveOrZero
    private final Integer quantity;

    @NotNull
    private final Integer manufacturerId;

    @ConstructorProperties({"id", "name", "description", "price", "quantity", "manufacturerId"})
    public ItemDto(Integer id, String name, String description,
                   BigDecimal price, Integer quantity, Integer manufacturerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.manufacturerId = manufacturerId;
    }
}
