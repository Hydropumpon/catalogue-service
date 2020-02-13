package com.example.catalogue.catalogueservice.controller.category;

import com.example.catalogue.catalogueservice.dto.CategoryDto;
import com.example.catalogue.catalogueservice.entity.Category;

public class CategoryTestUtils {

    private static final String CATEGORY_NAME = "category";

    private static final Integer CATEGORY_ID = 1;

    static final CategoryDto categoryDtoToAdd = CategoryDto.builder()
                                                        .name(CATEGORY_NAME)
                                                        .build();

    static final CategoryDto categoryDtoToUpdate = CategoryDto.builder()
                                                           .name(CATEGORY_NAME)
                                                           .id(CATEGORY_ID)
                                                           .build();

    static final Category categoryServiceAnswer = Category.builder()
                                                   .name(CATEGORY_NAME)
                                                   .id(CATEGORY_ID)
                                                   .build();

    static final CategoryDto categoryDtoAnswer = CategoryDto.builder()
                                                            .name(categoryServiceAnswer.getName())
                                                            .id(categoryServiceAnswer.getId())
                                                            .build();
}
