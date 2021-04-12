package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailid(String email);
    Optional<List<Employee>> findAllByManagerId(long id);
}
