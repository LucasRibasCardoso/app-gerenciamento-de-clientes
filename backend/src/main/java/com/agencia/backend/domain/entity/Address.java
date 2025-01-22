package com.agencia.backend.domain.entity;

public class Address {

  private final String zipCode;
  private final String country;
  private final String state;
  private final String city;
  private final String neighborhood;
  private final String street;
  private final String complement;
  private final String residentialNumber;

  public Address(
      String zipCode,
      String country,
      String state,
      String city,
      String neighborhood,
      String street,
      String complement,
      String residentialNumber
  ) {
    this.zipCode = zipCode;
    this.country = country;
    this.state = state;
    this.city = city;
    this.neighborhood = neighborhood;
    this.street = street;
    this.complement = complement;
    this.residentialNumber = residentialNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getCountry() {
    return country;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public String getStreet() {
    return street;
  }

  public String getComplement() {
    return complement;
  }

  public String getResidentialNumber() {
    return residentialNumber;
  }

}
