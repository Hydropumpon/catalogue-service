package com.example.catalogue.catalogueservice.repository;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.jointable.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Integer> {

    void deleteAllByCategoryId(Integer id);

    void deleteAllByCategoryInAndItem(List<Category> categories, Item item);

    void deleteAllByItem(Item item);
}