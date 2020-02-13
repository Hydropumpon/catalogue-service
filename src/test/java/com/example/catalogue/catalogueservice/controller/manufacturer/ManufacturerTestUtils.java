package com.example.catalogue.catalogueservice.controller.manufacturer;

import com.example.catalogue.catalogueservice.dto.AddressDto;
import com.example.catalogue.catalogueservice.dto.ManufacturerDto;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.entity.embeddable.Address;

public class ManufacturerTestUtils {


    private static final String MANUFACTURER_NAME = "manufacturer";
    private static final Integer MANUFACTURER_ID = 1;
    private static final String MANUFACTURER_FOUNDATION_YEAR = "2015";
    private static final String MANUFACTURER_EMAIL = "manufacturer@mail.ru";
    private static final String MANUFACTURER_ADDRESS_CITY = "TestCity";
    private static final String MANUFACTURER_ADDRESS_COUNTRY = "TestCountry";
    private static final String MANUFACTURER_ADDRESS_STREET = "TestStreet";
    private static final String MANUFACTURER_ADDRESS_ZIP = "123456";
    private static final Integer MANUFACTURER_ADDRESS_BUILDING = 1;

    static final AddressDto addressDto = AddressDto.builder()
                                                   .zip(MANUFACTURER_ADDRESS_ZIP)
                                                   .street(MANUFACTURER_ADDRESS_STREET)
                                                   .country(MANUFACTURER_ADDRESS_COUNTRY)
                                                   .city(MANUFACTURER_ADDRESS_CITY)
                                                   .buildingNumber(MANUFACTURER_ADDRESS_BUILDING)
                                                   .build();

    static final Address address = Address.builder()
                                             .zip(MANUFACTURER_ADDRESS_ZIP)
                                             .street(MANUFACTURER_ADDRESS_STREET)
                                             .country(MANUFACTURER_ADDRESS_COUNTRY)
                                             .city(MANUFACTURER_ADDRESS_CITY)
                                             .buildingNumber(MANUFACTURER_ADDRESS_BUILDING)
                                             .build();

    static final Manufacturer manufacturerToAdd = Manufacturer.builder()
                                                              .name(MANUFACTURER_NAME)
                                                              .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                              .email(MANUFACTURER_EMAIL)
                                                              .address(address)
                                                              .build();

    static final Manufacturer manufacturerToUpdate = Manufacturer.builder()
                                                              .id(MANUFACTURER_ID)
                                                              .name(MANUFACTURER_NAME)
                                                              .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                              .email(MANUFACTURER_EMAIL)
                                                              .address(address)
                                                              .build();

    static final ManufacturerDto manufacturerDtoToAdd = ManufacturerDto.builder()
                                                              .name(MANUFACTURER_NAME)
                                                              .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                              .email(MANUFACTURER_EMAIL)
                                                              .address(addressDto)
                                                              .build();

    static final Manufacturer manufacturerServiceAnswer = Manufacturer.builder()
                                                                      .name(MANUFACTURER_NAME)
                                                                      .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                                      .email(MANUFACTURER_EMAIL)
                                                                      .id(MANUFACTURER_ID)
                                                                      .address(address)
                                                                      .build();

    static final ManufacturerDto manufacturerDtoAnswer = ManufacturerDto.builder()
                                                                        .name(MANUFACTURER_NAME)
                                                                        .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                                        .email(MANUFACTURER_EMAIL)
                                                                        .id(MANUFACTURER_ID)
                                                                        .address(addressDto)
                                                                        .build();


    static final ManufacturerDto manufacturerDtoToUpdate = ManufacturerDto.builder()
                                                                          .id(MANUFACTURER_ID)
                                                                          .name(MANUFACTURER_NAME)
                                                                          .foundationYear(MANUFACTURER_FOUNDATION_YEAR)
                                                                          .email(MANUFACTURER_EMAIL)
                                                                          .id(MANUFACTURER_ID)
                                                                          .address(addressDto)
                                                                          .build();

}
