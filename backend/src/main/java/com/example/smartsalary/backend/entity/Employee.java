package com.example.smartsalary.backend.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "employees")
public class Employee {

  @Id
  @Column(name = "emp_no")
  private Long empNo;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "first_name")
  private String fname;

  @Column(name = "last_name")
  private String lname;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum('M', 'F')")
  private Gender gender;

  private LocalDate hire_date;

  @OneToMany(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "emp_no", updatable = false, insertable = false)
  private Set<Salary> salary;

  @OneToMany(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "emp_no", updatable = false, insertable = false)
  private Set<DepartmentStore> departmentStores;

  @OneToMany(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "emp_no", updatable = false, insertable = false)
  private Set<Title> titles;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return getEmpNo().equals(employee.getEmpNo()) && Objects
        .equals(getBirthDate(), employee.getBirthDate()) && Objects
        .equals(getFname(), employee.getFname()) && Objects
        .equals(getLname(), employee.getLname()) && getGender() == employee.getGender()
        && Objects.equals(getHire_date(), employee.getHire_date());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getEmpNo(), getBirthDate(), getFname(), getLname(), getGender(), getHire_date());
  }
}