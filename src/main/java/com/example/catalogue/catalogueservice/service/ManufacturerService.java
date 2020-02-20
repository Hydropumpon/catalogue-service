package com.example.catalogue.catalogueservice.service;

import com.example.catalogue.catalogueservice.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ManufacturerService {

    Manufacturer addManufacturer(Manufacturer manufacturer);

    Page<Manufacturer> getManufacturers(Pageable pageable);

    List<Manufacturer> getManufacturersByIdIn(List<Integer> ids);

    Manufacturer updateManufacturer(Manufacturer manufacturer, Integer id);

    void deleteManufacturer(Integer id);

    Manufacturer getManufacturerById(Integer id);
}
