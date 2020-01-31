package com.example.catalogue.catalogueservice.repository;

import com.example.catalogue.catalogueservice.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Query(nativeQuery = true, value = "select * from manufacturer where  id in :ids")
    List<Manufacturer> findManufacturersByIdIn(@Param("ids") List<Integer> ids);

}
