package com.example.catalogue.catalogueservice.entity;

import com.example.catalogue.catalogueservice.entity.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Manufacturer {
    @Id
    @SequenceGenerator(name = "manufacturer_id_gen", sequenceName = "manufacturer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "manufacturer_id_gen")
    @Column(name = "id")
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "foundation_year")
    private String foundationYear;

    @Email
    private String email;

    @Embedded
    @AttributeOverride(name = "buildingNumber", column = @Column(name = "building_number"))
    private Address address;

}
