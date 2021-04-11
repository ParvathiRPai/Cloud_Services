package com.example.smartsalary.backend.controller;

import com.example.smartsalary.backend.controllerModels.EmployeeApiModel;
import com.example.smartsalary.backend.entity.Employee;
import com.example.smartsalary.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/smartapp")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeApiModel mapEmployee(Employee employee)
    {
        String managerEmailId;
        try
        {
            Employee manager= employeeRepository.findById(employee.managerId)
                    .orElseThrow(() -> new ResourceAccessException("Id not found"));
            managerEmailId = manager.getEmailid();
        }
        catch(Exception ex)
        {
            managerEmailId = "None";
        }

        var emp = new EmployeeApiModel();
        emp.id = employee.id;
        emp.managerEmail = managerEmailId;
        emp.emailid = employee.emailid;
        emp.first_name = employee.first_name;
        emp.last_name = employee.last_name;
        emp.salary = employee.salary;
        return emp;
    }
    // get all employees
    @GetMapping("/employee")
    public List<EmployeeApiModel> getAllEmployees()
    {
        var employees = employeeRepository.findAll();
        var emps = employees.stream().map(employee -> {return mapEmployee(employee);}).collect(Collectors.toList());
        return emps;
    }

    //Create employee rest api
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody EmployeeApiModel employee)
    {
        long managerId;
        try
        {
            Employee manager= employeeRepository.findByEmailid(employee.managerEmail)
                    .orElseThrow(() -> new ResourceAccessException("Id not found"));
            managerId = manager.getId();
        }
        catch(Exception ex)
        {
            managerId = 0;
        }
        var emp = new Employee();
        emp.managerId = managerId;
        emp.emailid = employee.emailid;
        emp.first_name = employee.first_name;
        emp.last_name = employee.last_name;
        emp.salary = employee.salary;

        return employeeRepository.save(emp);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeApiModel> getEmployeeById(@PathVariable Long id)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        var emp = mapEmployee(employee);
        return ResponseEntity.ok(emp);

    }

    @GetMapping("/employee/emails/{email}")
    public ResponseEntity<EmployeeApiModel> getEmployeeByEmail(@PathVariable String email)
    {
        Employee employee= employeeRepository.findByEmailid(email)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        var emp = mapEmployee(employee);
        return ResponseEntity.ok(emp);

    }

    @GetMapping("/employee/directReports/{email}")
    public ResponseEntity<List<EmployeeApiModel>> getEmployeeByManager(@PathVariable String email)
    {
        Employee manager = employeeRepository.findByEmailid(email)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));

        List<Employee> employee= employeeRepository.findAllByManagerId(manager.id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        var emps = employee.stream().map(emp -> {return mapEmployee(emp);}).collect(Collectors.toList());

        return ResponseEntity.ok(emps);

    }

    // Update Employee
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody EmployeeApiModel employeeDetails)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        long managerId;
        try {
            Employee manager= employeeRepository.findByEmailid(employeeDetails.managerEmail)
                    .orElseThrow(() -> new ResourceAccessException("Id not found"));
            managerId = manager.getId();
        }
        catch(Exception ex)
        {
            managerId = 0;
        }

        employee.setFirst_name(employeeDetails.first_name);
        employee.setLast_name(employeeDetails.last_name);
        employee.setEmailid(employeeDetails.emailid);
        employee.setManagerId(managerId);
        employee.setSalary(employeeDetails.salary);

        Employee updatedEmployee=employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete Employee
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Id not found"));
        employeeRepository.delete(employee);
        Map<String, Boolean> response=new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
