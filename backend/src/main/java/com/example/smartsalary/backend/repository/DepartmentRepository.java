package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Department;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {

  Optional<Department> findByDname(String dname);
}
