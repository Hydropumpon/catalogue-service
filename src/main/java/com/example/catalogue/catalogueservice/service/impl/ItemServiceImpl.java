package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.entity.jointable.ItemCategory;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.NotFoundException;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.repository.CategoryRepository;
import com.example.catalogue.catalogueservice.repository.ItemCategoryRepository;
import com.example.catalogue.catalogueservice.repository.ItemRepository;
import com.example.catalogue.catalogueservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    private CategoryRepository categoryRepository;

    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           CategoryRepository categoryRepository,
                           ItemCategoryRepository itemCategoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> getItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Item addItem(Item item, List<Integer> categoryIds) {
        Item itemSaved = itemRepository.save(item);
        List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND);
        }
        List<ItemCategory> itemCategories = new ArrayList<>(categories.size());
        categories.forEach((category -> itemCategories.add(new ItemCategory(item, category))));
        itemCategoryRepository.saveAll(itemCategories);
        return itemSaved;
    }

    @Override
    @Transactional
    public Item updateItem(Item item, List<Integer> categoryIds, Integer id) {

        Item itemSaved = itemRepository.findById(id).map(itemDb ->
                                                         {
                                                             itemDb.setQuantity(item.getQuantity());
                                                             itemDb.setDescription(item.getDescription());
                                                             itemDb.setManufacturer(item.getManufacturer());
                                                             itemDb.setName(item.getName());
                                                             itemDb.setPrice(item.getPrice());
                                                             return itemRepository.save(itemDb);
                                                         }).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND, ServiceErrorCode.NOT_FOUND));

        List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND, ServiceErrorCode.NOT_FOUND);
        }

        List<Category> currentCategories = categoryRepository.findCategoriesByItemId(itemSaved.getId());
        List<Category> categoriesToDelete = new ArrayList<>(currentCategories);
        categoriesToDelete.removeAll(categories);

        itemCategoryRepository.deleteAllByCategoryInAndItem(categoriesToDelete, itemSaved);

        List<Category> categoriesToInsert = new ArrayList<>(categories);
        categoriesToInsert.removeAll(currentCategories);

        List<ItemCategory> itemCategories = new ArrayList<>(categoriesToInsert.size());
        categoriesToInsert.forEach((category -> itemCategories.add(new ItemCategory(item, category))));
        itemCategoryRepository.saveAll(itemCategories);
        return itemSaved;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND, ServiceErrorCode.NOT_FOUND));

    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getByCategory(String categoryName) {
        return itemRepository.findByCategoryName(categoryName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getByCategoryAndManufacturer(String categoryName, String manufacturerName) {
        return itemRepository.findByCategoryNameAndManufacturerName(categoryName, manufacturerName);
    }

    @Override
    @Transactional
    public void deleteItemsByManufacturer(Manufacturer manufacturer) {
        List<Item> items = itemRepository.findAllByManufacturer(manufacturer);
        items.forEach(item -> itemCategoryRepository.deleteAllByItem(item));
        itemRepository.deleteAll(items);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> getItemsByIdAndQuantity(Integer itemId, Integer quantity) {
        return itemRepository.findByIdAndQuantityGreaterThanEqual(itemId, quantity);
    }

    @Override
    @Transactional
    public void deleteItem(Integer id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND, ServiceErrorCode.NOT_FOUND));
        itemCategoryRepository.deleteAllByItem(item);
        itemRepository.delete(item);
    }
}
