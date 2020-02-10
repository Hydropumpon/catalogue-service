package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ItemService {

    Page<Item> getItems(Pageable pageable);

    Item addItem(Item item, List<Integer> categoryIds);

    Item updateItem(Item item, List<Integer> categoryIds);

    Item getItemById(Integer id);

    List<Item> getItemsByPriceLessAndManufacturer(BigDecimal price, String manufacturer);

    List<Item> getByCategory(String categoryName);

    List<Item> getByCategoryAndManufacturer(String categoryName, String manufacturerName);

    List<BigDecimal> findByIdInOrdered(List<Integer> ids);

    void deleteItemsByManufacturer(Manufacturer manufacturer);
}
