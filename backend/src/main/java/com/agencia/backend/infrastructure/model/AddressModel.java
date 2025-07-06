package com.agencia.backend.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_addresses")
@Getter
@Setter
@NoArgsConstructor
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipCode;
    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String complement;
    private String residentialNumber;

    public AddressModel(
            Long id,
            String zipCode,
            String country,
            String state,
            String city,
            String neighborhood,
            String street,
            String complement,
            String residentialNumber
    ) {
        this.id = id;
        this.zipCode = zipCode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.complement = complement;
        this.residentialNumber = residentialNumber;
    }
}
