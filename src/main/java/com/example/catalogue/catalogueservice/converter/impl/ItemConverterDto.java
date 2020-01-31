package com.example.catalogue.catalogueservice.converter.impl;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.ItemDto;
import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemConverterDto implements ConverterDto<Item, ItemDto> {

    private ManufacturerService manufacturerService;

    @Autowired
    public ItemConverterDto(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Item toEntity(ItemDto dto) {
        return Item.builder()
                   .id(dto.getId())
                   .description(dto.getDescription())
                   .name(dto.getName())
                   .price(dto.getPrice())
                   .manufacturer(manufacturerService.getManufacturerById(dto.getManufacturerId()))
                   .build();
    }

    @Override
    public ItemDto toDto(Item entity) {
        return ItemDto.builder()
                      .description(entity.getDescription())
                      .manufacturerId(entity.getManufacturer().getId())
                      .name(entity.getName())
                      .price(entity.getPrice())
                      .id(entity.getId())
                      .build();
    }
}
