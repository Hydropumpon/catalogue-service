package com.example.catalogue.catalogueservice.converter.impl;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.CategoryDto;
import com.example.catalogue.catalogueservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverterDto implements ConverterDto<Category, CategoryDto> {
    @Override
    public Category toEntity(CategoryDto dto) {
        return Category.builder()
                       .id(dto.getId())
                       .name(dto.getName())
                       .build();
    }

    @Override
    public CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                          .id(entity.getId())
                          .name(entity.getName())
                          .build();
    }
}
