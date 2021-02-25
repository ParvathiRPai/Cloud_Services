package com.example.smartsalary.backend.controller;

import com.example.smartsalary.backend.entity.Employee;
import com.example.smartsalary.backend.entity.Manager;
import com.example.smartsalary.backend.repository.EmployeeRepository;
import com.example.smartsalary.backend.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/smartapp")
public class EmployeeController {
    @Autowired
    private EmployeeRepository  employeeRepository;
    @GetMapping("/employee")

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody  Employee employee)
    {
        return employeeRepository.save(employee);
    }

}
