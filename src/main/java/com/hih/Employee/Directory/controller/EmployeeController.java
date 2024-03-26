package com.hih.Employee.Directory.controller;

import com.hih.Employee.Directory.dto.AddressDTO;
import com.hih.Employee.Directory.dto.EmployeeDTO;
import com.hih.Employee.Directory.entity.Address;
import com.hih.Employee.Directory.entity.Employee;
import com.hih.Employee.Directory.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employeeDetails = convertToEntity(employeeDTO);
        Employee newEmployee = employeeService.addEmployee(employeeDetails);
        EmployeeDTO employeeResponse = convertToDTO(newEmployee);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        EmployeeDTO employeeResponse = convertToDTO(employee);
        return ResponseEntity.ok(employeeResponse);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        EmployeeDTO employeeResponse = convertToDTO(updatedEmployee);
        return ResponseEntity.ok(employeeResponse);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOS);
    }

    // Converts EmployeeDTO to Employee entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        return employee;
    }

    // Converts Employee entity to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId( employee.getEmployeeId() );
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());

        if (employee.getAddresses() != null) {
            List<AddressDTO> addressDTOs = employee.getAddresses().stream()
                    .map(this::convertAddressToDTO)
                    .collect(Collectors.toList());
            employeeDTO.setAddresses(addressDTOs);
        }

        return employeeDTO;
    }


    @PostMapping("/{employeeId}/addresses")
    public ResponseEntity<EmployeeDTO> addAddressToEmployee(@PathVariable Long employeeId, @RequestBody AddressDTO addressDTO) {
        Employee updatedEmployee = employeeService.addAddress(employeeId, addressDTO);
        EmployeeDTO employeeResponse = convertToDTO(updatedEmployee);
        return ResponseEntity.ok(employeeResponse);
    }

    // New endpoint to update an employee's address
    @PatchMapping("/{employeeId}/addresses/{addressId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeAddress(@PathVariable Long employeeId, @PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        Employee updatedEmployee = employeeService.updateAddress(employeeId, addressId, addressDTO);
        EmployeeDTO employeeResponse = convertToDTO(updatedEmployee);
        return ResponseEntity.ok(employeeResponse);
    }

    // New endpoint to delete an employee's address
    @DeleteMapping("/{employeeId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddressFromEmployee(@PathVariable Long employeeId, @PathVariable Long addressId) {
        employeeService.deleteAddress(employeeId, addressId);
        return ResponseEntity.ok().build();
    }

    // New endpoint to get all addresses for an employee
    @GetMapping("/{employeeId}/addresses")
    public ResponseEntity<List<AddressDTO>> getEmployeeAddresses(@PathVariable Long employeeId) {
        List<AddressDTO> addresses = employeeService.getAddressesByEmployeeId(employeeId);
        return ResponseEntity.ok(addresses);
    }

    private AddressDTO convertAddressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId()); // Include this if you want to expose the ID
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setStreetNo(address.getStreetNo());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }

    private Address convertDTOToAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setStreet(addressDTO.getStreet());
        address.setStreetNo(addressDTO.getStreetNo());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }
}