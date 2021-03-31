package com.example.smartsalary.backend.controller;

import com.example.smartsalary.backend.entity.Employee;
import com.example.smartsalary.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/smartapp")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // get all employees
    @GetMapping("/employee")
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    //Create employee rest api
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody  Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        return ResponseEntity.ok(employee);

    }
    // Update Employee
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));

        employee.setFirst_name(employeeDetails.getFirst_name());
        employee.setLast_name(employeeDetails.getLast_name());
        employee.setEmailid(employeeDetails.getEmailid());

        Employee updatedEmployee=employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }




}
