package com.hih.Employee.Directory.service;

import com.hih.Employee.Directory.dto.AddressDTO;
import com.hih.Employee.Directory.dto.EmployeeDTO;
import com.hih.Employee.Directory.entity.Address;
import com.hih.Employee.Directory.entity.Employee;
import com.hih.Employee.Directory.repository.AddressRepository;
import com.hih.Employee.Directory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;


    public EmployeeService(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new RuntimeException("Employee with id: " + employeeId + " not found.");
        }
    }

    /*
     public Employee updateEmployee(Long employeeId, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());
            //need to update address somehow

            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee with id: " + employeeId + " not found.");
        }
    }
    */

    public Employee updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            if (employeeDTO.getName() != null) {
                employee.setName(employeeDTO.getName());
            }
            if (employeeDTO.getEmail() != null) {
                employee.setEmail(employeeDTO.getEmail());
            }
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee with id: " + employeeId + " not found.");
        }
    }

    public Employee addAddress(Long employeeId, AddressDTO addressDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        Address address = new Address();
        // Map AddressDTO to Address entity here
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setStreetNo(addressDTO.getStreetNo());
        address.setZipCode(addressDTO.getZipCode());

        employee.getAddresses().add(address);
        address.setEmployee(employee);

        return employeeRepository.save(employee);
    }

    public Employee updateAddress(Long employeeId, Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        if (!address.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("Address does not belong to the specified employee");
        }

        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setStreetNo(addressDTO.getStreetNo());
        address.setZipCode(addressDTO.getZipCode());

        return employeeRepository.save(address.getEmployee());
    }

    public void deleteAddress(Long employeeId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        if (!address.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("Address does not belong to the specified employee");
        }

        addressRepository.deleteById(addressId);
    }

    public List<AddressDTO> getAddressesByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        return employee.getAddresses().stream()
                .map(address -> new AddressDTO(address.getCity(), address.getState(), address.getStreet(),
                        address.getStreetNo(), address.getZipCode()))
                .collect(Collectors.toList());
    }
}
