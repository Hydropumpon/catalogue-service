package com.example.catalogue.catalogueservice.controller;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.ManufacturerDto;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("catalogue/manufacturer")
public class ManufacturerController {

    private ConverterDto<Manufacturer, ManufacturerDto> manufacturerConverterDto;

    private ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(
            ConverterDto<Manufacturer, ManufacturerDto> manufacturerConverter,
            ManufacturerService manufacturerService) {
        this.manufacturerConverterDto = manufacturerConverter;
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public ManufacturerDto addManufacturer(@RequestBody @Valid ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerConverterDto.toEntity(manufacturerDto);
        return manufacturerConverterDto.toDto(manufacturerService.addManufacturer(manufacturer));
    }

    @GetMapping
    public List<ManufacturerDto> getManufacturers(Pageable pageable) {
        return manufacturerConverterDto.toDto(manufacturerService.getManufacturers(pageable).getContent());
    }

    @GetMapping("/byIdIn")
    public List<ManufacturerDto> getManufacturersByIdIn(@RequestParam("ids") List<Integer> ids) {
        return manufacturerConverterDto.toDto(manufacturerService.getManufacturersByIdIn(ids));
    }

    @PutMapping
    public ManufacturerDto updateManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerConverterDto.toEntity(manufacturerDto);
        return manufacturerConverterDto.toDto(manufacturerService.updateManufacturer(manufacturer));
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable(name = "id") Integer id) {
        manufacturerService.deleteManufacturer(id);
    }

    @GetMapping("/{id}")
    public ManufacturerDto getManufacturedById(@PathVariable("id") Integer id) {
        return manufacturerConverterDto.toDto(manufacturerService.getManufacturerById(id));
    }

}
