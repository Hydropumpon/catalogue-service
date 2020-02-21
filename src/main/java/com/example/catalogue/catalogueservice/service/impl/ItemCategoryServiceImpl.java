package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.jointable.ItemCategory;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.NotFoundException;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.repository.ItemCategoryRepository;
import com.example.catalogue.catalogueservice.service.CategoryService;
import com.example.catalogue.catalogueservice.service.ItemCategoryService;
import com.example.catalogue.catalogueservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private ItemService itemService;

    private CategoryService categoryService;

    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    public ItemCategoryServiceImpl(ItemService itemService,
                                   CategoryService categoryService,
                                   ItemCategoryRepository itemCategoryRepository) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Override
    @Transactional
    public List<Category> addCategories(Integer itemId, List<Integer> categoryIds) {
        Item item = itemService.getItem(itemId);
        List<Category> categoryList = categoryService.getCategoriesByIdIn(categoryIds);
        if (categoryIds.size() != categoryList.size()) {
            throw new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND);
        }

        List<Category> currentCategories = itemCategoryRepository.findAllByItem(item)
                                                                 .stream()
                                                                 .map(ItemCategory::getCategory)
                                                                 .collect(Collectors.toList());
        List<Category> categoriesToDelete = new ArrayList<>(currentCategories);
        categoriesToDelete.removeAll(categoryList);
        itemCategoryRepository.deleteAllByCategoryInAndItem(categoriesToDelete, item);

        List<Category> categoriesToInsert = new ArrayList<>(categoryList);
        categoriesToInsert.removeAll(currentCategories);
        List<ItemCategory> itemCategories = categoriesToInsert.stream()
                                                              .map(category -> new ItemCategory(item, category))
                                                              .collect(Collectors.toList());
        itemCategoryRepository.saveAll(itemCategories);
        return categoryList;
    }

    @Override
    @Transactional
    public void deleteCategoriesByItemId(Integer itemId) {
        Item item = itemService.getItem(itemId);
        this.deleteCategoriesByItem(item);
    }

    @Override
    @Transactional
    public void deleteCategoriesByItem(Item item) {
        itemCategoryRepository.deleteAllByItem(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getItemCategories(Integer itemId) {
        Item item = itemService.getItem(itemId);
        return itemCategoryRepository.findAllByItem(item)
                                     .stream()
                                     .map(ItemCategory::getCategory)
                                     .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCategoriesByCategory(Category category) {
        itemCategoryRepository.deleteAllByCategory(category);
    }


}
