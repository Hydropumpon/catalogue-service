package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.exception.ConflictException;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.NotFoundException;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.repository.CategoryRepository;
import com.example.catalogue.catalogueservice.service.CategoryService;
import com.example.catalogue.catalogueservice.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ItemCategoryService itemCategoryService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               @Lazy ItemCategoryService itemCategoryService) {
        this.categoryRepository = categoryRepository;
        this.itemCategoryService = itemCategoryService;
    }


    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Category addCategory(Category category) {
        categoryRepository.findByName(category.getName())
                          .ifPresent(cat -> {
                              throw new ConflictException(ErrorMessage.CATEGORY_EXIST, ServiceErrorCode.ALREADY_EXIST);
                          });
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category, Integer id) {
        return categoryRepository.findById(id)
                                 .map(categoryDb -> {
                                     categoryDb.setName(category.getName());
                                     return categoryRepository.save(categoryDb);
                                 })
                                 .orElseThrow(() -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND,
                                                                          ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.findById(id).map(category -> {
            itemCategoryService.deleteCategoriesByCategory(category);
            categoryRepository.delete(category);
            return Optional.empty();
        }).orElseThrow(() -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoriesByIdIn(List<Integer> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds);
    }
}
