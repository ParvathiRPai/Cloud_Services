package com.example.smartsalary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="managers")
public class Manager {
    @Id
    @GeneratedValue
    private int emp_no;
    private String birth_date;
    private String first_name;
    private String last_name;
    private String gender;
    private String hire_date;
    private String dept_no;
    private String from_date;
    private String to_date;

}
