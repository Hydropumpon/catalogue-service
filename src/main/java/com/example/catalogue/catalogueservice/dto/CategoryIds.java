package com.example.catalogue.catalogueservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class CategoryIds {

    private final List<Integer> categoryIds;

    @ConstructorProperties("categoryIds")
    public CategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

}
