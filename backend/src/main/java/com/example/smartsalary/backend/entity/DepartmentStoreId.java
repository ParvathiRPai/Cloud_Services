package com.example.smartsalary.backend.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentStoreId implements Serializable {

  long emp_no;
  String deptNo;
}
