package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Employee;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByFnameIgnoreCaseAndLnameIgnoreCaseAndBirthDate(String fname, String lName,
      LocalDate b);

  List<Employee> findByFnameIgnoreCaseAndLnameIgnoreCase(String fname, String lName);

  @Query("SELECT max(e.empNo) FROM Employee e")
  Long getMaxEmpNo();
}
