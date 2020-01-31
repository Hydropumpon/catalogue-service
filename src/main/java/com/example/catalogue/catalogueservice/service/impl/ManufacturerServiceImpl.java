package com.example.catalogue.catalogueservice.service.impl;

import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.NotFoundException;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.repository.ManufacturerRepository;
import com.example.catalogue.catalogueservice.service.ManufacturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(
            ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    @Transactional
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Manufacturer> getManufacturers(Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Manufacturer> getManufacturersByIdIn(List<Integer> ids) {
        return manufacturerRepository.findManufacturersByIdIn(ids);
    }

    @Override
    @Transactional
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    @Transactional
    public void deleteManufacturer(Integer id) {
        manufacturerRepository.findById(id).ifPresent((manufacturer -> manufacturerRepository.delete(manufacturer)));
    }

    @Override
    public Manufacturer getManufacturerById(Integer id) {
        return manufacturerRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MANUFACTURER_NOT_FOUND,
                                            ServiceErrorCode.NOT_FOUND));
    }
}
