package com.example.catalogue.catalogueservice.controller;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.CategoryDto;
import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("catalogue/category")
public class CategoryController {

    private CategoryService categoryService;

    private ConverterDto<Category, CategoryDto> categoryConverterDto;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ConverterDto<Category, CategoryDto> categoryConverterDto) {
        this.categoryService = categoryService;
        this.categoryConverterDto = categoryConverterDto;
    }

    @GetMapping()
    public List<CategoryDto> getCategories(Pageable pageable) {
        return categoryConverterDto.toDto(categoryService.getCategories(pageable).getContent());
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable("id") Integer id) {
        return categoryConverterDto.toDto(categoryService.getCategoryById(id));
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryConverterDto.toEntity(categoryDto);
        return categoryConverterDto.toDto(categoryService.addCategory(category));
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto, @PathVariable("id") Integer id) {
        Category category = categoryConverterDto.toEntity(categoryDto);
        return categoryConverterDto.toDto(categoryService.updateCategory(category, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
    }

}
