package com.example.catalogue.catalogueservice.converter.impl;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.AddressDto;
import com.example.catalogue.catalogueservice.dto.ManufacturerDto;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.entity.embeddable.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerConverterDto implements ConverterDto<Manufacturer, ManufacturerDto> {

    private ConverterDto<Address, AddressDto> addressDtoConverter;

    @Autowired
    public ManufacturerConverterDto(
            ConverterDto<Address, AddressDto> addressDtoConverter) {
        this.addressDtoConverter = addressDtoConverter;
    }

    @Override
    public Manufacturer toEntity(ManufacturerDto dto) {
        return Manufacturer.builder()
                           .email(dto.getEmail())
                           .foundationYear(dto.getFoundationYear())
                           .name(dto.getName())
                           .address(addressDtoConverter.toEntity(dto.getAddress()))
                           .id(dto.getId())
                           .build();
    }

    @Override
    public ManufacturerDto toDto(Manufacturer entity) {
        return ManufacturerDto.builder()
                              .email(entity.getEmail())
                              .foundationYear(entity.getFoundationYear())
                              .id(entity.getId())
                              .address(addressDtoConverter.toDto(entity.getAddress()))
                              .name(entity.getName())
                              .build();
    }
}
