package com.example.smartsalary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "departments")
public class Department {

  @Id
  @Column(name = "dept_no")
  private String deptNo;

  @Column(name = "dept_name")
  private String dname;

  @OneToOne(cascade = CascadeType.REFRESH)
  @JoinTable(name = "dept_manager", joinColumns = {
      @JoinColumn(name = "dept_no", updatable = false, insertable = false)}, inverseJoinColumns = {
      @JoinColumn(name = "emp_no", updatable = false, insertable = false)})
  @JsonIgnoreProperties({"departmentStores", "salary"})
  private Employee manager;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Department that = (Department) o;
    return getDeptNo().equals(that.getDeptNo()) && getDname().equals(that.getDname());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDeptNo(), getDname());
  }

  @Override
  public String toString() {
    return "Department{" +
        "deptNo='" + deptNo + '\'' +
        ", dname='" + dname + '\'' +
        '}';
  }
}
