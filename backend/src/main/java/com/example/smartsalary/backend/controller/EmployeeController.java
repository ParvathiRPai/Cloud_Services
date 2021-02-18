package com.example.smartsalary.backend.controller;

import com.example.smartsalary.backend.entity.Manager;
import com.example.smartsalary.backend.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/smartapp")
public class EmployeeController {

    @Autowired
    private ManagerRepository managerRepository;

    // get all employees

    @GetMapping("/manager")
    public List<Manager> getAllEmployees()
    {
        return managerRepository.findAll();
    }


}
