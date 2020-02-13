package com.example.catalogue.catalogueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.beans.ConstructorProperties;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AddressDto {

    @NotBlank
    private final String country;

    @NotBlank
    private final String city;

    @NotBlank
    private final String street;

    @NotBlank
    private final String zip;

    @Positive
    private final Integer buildingNumber;

    @ConstructorProperties({"country", "city", "street", "zip", "buildingNumber"})
    public AddressDto(String country, String city, String street, String zip, Integer buildingNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.zip = zip;
        this.buildingNumber = buildingNumber;
    }
}
