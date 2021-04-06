package com.example.smartsalary.backend.entity;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryId implements Serializable {

  private Long emp_no;

  private LocalDate from_date;
}
