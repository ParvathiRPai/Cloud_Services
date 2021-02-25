package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Employee;
import com.example.smartsalary.backend.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
