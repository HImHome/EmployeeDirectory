package com.hih.Employee.Directory.dto;

public class AddressDTO {
    private Long addressId;
    private String city;
    private String state;
    private String street;
    private String streetNo;
    private String zipCode;

    public AddressDTO() {
    }

    public AddressDTO(String city, String state, String street, String streetNo, String zipCode) {
        this.city = city;
        this.state = state;
        this.street = street;
        this.streetNo = streetNo;
        this.zipCode = zipCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
