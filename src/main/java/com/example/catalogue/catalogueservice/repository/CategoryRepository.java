package com.example.catalogue.catalogueservice.repository;

import com.example.catalogue.catalogueservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    List<Category> findAllByIdIn(List<Integer> categoryIds);

    @Query(nativeQuery = true, value = "select cat from category cat " +
            "join item_category ic on cat.id = ic.category_id " +
            "where ic.item_id = :itemId")
    List<Category> findCategoriesByItemId(@Param("itemId") Integer id);
}
