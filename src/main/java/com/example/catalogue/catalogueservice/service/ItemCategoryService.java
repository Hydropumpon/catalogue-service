package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.entity.Item;

import java.util.List;

public interface ItemCategoryService {

    List<Category> addCategories(Integer itemId, List<Integer> categoryIds);

    void deleteCategoriesByItemId(Integer itemId);

    void deleteCategoriesByItem(Item item);

    List<Category> getItemCategories(Integer itemId);

    void deleteCategoriesByCategory(Category category);
}
