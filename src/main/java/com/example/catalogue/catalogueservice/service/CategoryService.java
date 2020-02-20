package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category getCategoryById(Integer id);

    Page<Category> getCategories(Pageable pageable);

    Category addCategory(Category category);

    Category updateCategory(Category category, Integer id);

    void deleteCategory(Integer id);
}
