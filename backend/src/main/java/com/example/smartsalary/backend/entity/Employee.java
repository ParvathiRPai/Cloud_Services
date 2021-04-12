package com.example.smartsalary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue
    public  Long  id;
    public String  first_name;
    public String last_name;
    public String  emailid;
    public Long  managerId;
    public double  salary;
}
