package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Category;

import java.util.List;

public interface ItemCategoryService {

    List<Category> addCategories(Integer itemId, List<Integer> categoryIds);

    void deleteCategories(Integer itemId);

    List<Category> getItemCategories(Integer itemId);
}
