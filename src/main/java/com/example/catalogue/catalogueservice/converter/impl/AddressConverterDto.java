package com.example.catalogue.catalogueservice.converter.impl;

import com.example.catalogue.catalogueservice.converter.ConverterDto;
import com.example.catalogue.catalogueservice.dto.AddressDto;
import com.example.catalogue.catalogueservice.entity.embeddable.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverterDto implements ConverterDto<Address, AddressDto> {
    @Override
    public Address toEntity(AddressDto dto) {
        return Address.builder()
                      .buildingNumber(dto.getBuildingNumber())
                      .city(dto.getCity())
                      .country(dto.getCountry())
                      .street(dto.getStreet())
                      .zip(dto.getZip())
                      .build();
    }

    @Override
    public AddressDto toDto(Address entity) {
        return AddressDto.builder()
                         .buildingNumber(entity.getBuildingNumber())
                         .city(entity.getCity())
                         .country(entity.getCountry())
                         .street(entity.getStreet())
                         .zip(entity.getZip())
                         .build();
    }
}
