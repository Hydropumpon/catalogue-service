package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.exception.ConflictException;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.NotFoundException;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.repository.CategoryRepository;
import com.example.catalogue.catalogueservice.repository.ItemCategoryRepository;
import com.example.catalogue.catalogueservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ItemCategoryRepository itemCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Integer id) {
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
    public Category updateCategory(Category category) {
        checkNameDuplicate(category);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.findById(id).map(category -> {
            itemCategoryRepository.deleteAllByCategoryId(category.getId());
            categoryRepository.delete(category);
            return Optional.empty();
        }).orElseThrow(() -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
    }

    private void checkNameDuplicate(Category category) {
        categoryRepository.findByName(category.getName())
                          .map(Category::getId)
                          .filter(id -> !id.equals(category.getId()))
                          .ifPresent(integer -> {
                              throw new ConflictException(ErrorMessage.CATEGORY_EXIST,
                                                          ServiceErrorCode.ALREADY_EXIST);
                          });
    }
}
