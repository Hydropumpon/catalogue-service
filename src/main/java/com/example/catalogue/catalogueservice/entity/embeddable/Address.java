package com.example.catalogue.catalogueservice.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    private String country;

    private String city;

    private String street;

    private String zip;

    private Integer buildingNumber;
}
