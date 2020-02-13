package com.example.catalogue.catalogueservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Getter
@Builder
@EqualsAndHashCode
public class ManufacturerDto {

    private final Integer id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String foundationYear;

    @Email
    private final String email;

    @Valid
    private final AddressDto address;

    @ConstructorProperties({"id", "name", "foundationYear", "email", "address"})
    public ManufacturerDto(Integer id, @NotBlank String name, @NotBlank String foundationYear, @Email String email,
                           @Valid AddressDto address) {
        this.id = id;
        this.name = name;
        this.foundationYear = foundationYear;
        this.email = email;
        this.address = address;
    }
}
