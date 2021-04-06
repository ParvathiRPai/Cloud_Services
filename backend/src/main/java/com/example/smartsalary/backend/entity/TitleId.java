package com.example.smartsalary.backend.entity;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TitleId implements Serializable {

  private Long emp_no;
  private String title;

  private LocalDate from_date;
}
