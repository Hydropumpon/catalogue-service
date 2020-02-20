package com.example.catalogue.catalogueservice.repository;

import com.example.catalogue.catalogueservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    List<Category> findAllByIdIn(List<Integer> categoryIds);

}
