package com.hih.Employee.Directory.dto;

import com.hih.Employee.Directory.entity.Address;

import java.util.List;

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private List<AddressDTO> addresses;


    public EmployeeDTO() {
    }

    public EmployeeDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
