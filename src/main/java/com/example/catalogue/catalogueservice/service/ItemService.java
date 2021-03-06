package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Page<Item> getItems(Pageable pageable);

    Item addItem(Item item);

    Item updateItem(Item item, Integer id);

    Item getItem(Integer id);

    List<Item> getByCategory(String categoryName);

    List<Item> getByCategoryAndManufacturer(String categoryName, String manufacturerName);

    void deleteItemsByManufacturer(Manufacturer manufacturer);

    Optional<Item> getItemsByIdAndQuantity(Integer itemId, Integer quantity);

    void deleteItem(Integer id);

    List<Item> getByName(String name);
}
