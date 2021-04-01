package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
