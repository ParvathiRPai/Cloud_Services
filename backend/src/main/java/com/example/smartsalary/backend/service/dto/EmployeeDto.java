package com.example.smartsalary.backend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {

  private Long emp_no;
  private String birth_date;
  private String first_name;
  private String last_name;
  private String gender;
  private String emailid;
  private String hire_date;
  private long salary;
  private String department;
  private String title;
  private String manager;
  private boolean currentEmployee;
}
