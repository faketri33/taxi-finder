package org.faketri.domain.entity.address.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private Double latitude;
    private Double longitude;

    @Column(length = 300)
    private String rawAddress;

    @Column(length = 300)
    private String formattedAddress;

    private String city;
    private String street;
    private String houseNumber;
    private String country;
    private String postalCode;

    public Address(Double latitude, Double longitude, String rawAddress, String formattedAddress, String city, String street, String houseNumber, String country, String postalCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rawAddress = rawAddress;
        this.formattedAddress = formattedAddress;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRawAddress() {
        return rawAddress;
    }

    public void setRawAddress(String rawAddress) {
        this.rawAddress = rawAddress;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
