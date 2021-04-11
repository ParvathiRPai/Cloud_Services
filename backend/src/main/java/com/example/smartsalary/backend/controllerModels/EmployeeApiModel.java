package com.example.smartsalary.backend.controllerModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class EmployeeApiModel {
    public String  first_name;
    public String last_name;
    public String  emailid;
    public String  managerEmail;
    public double  salary;
}