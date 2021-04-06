package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Salary;
import com.example.smartsalary.backend.entity.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {

  @Modifying
  @Query("delete from Salary b where b.emp_no=:empNo")
  void deleteAllSalaries(@Param("empNo") Long empNo);
}
