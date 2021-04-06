package com.example.smartsalary.backend.entity;


import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "dept_emp")
@IdClass(DepartmentStoreId.class)
@Builder
public class DepartmentStore {

  @Id
  private long emp_no;

  @Id
  @Column(name = "dept_no")
  private String deptNo;

  @OneToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "dept_no", updatable = false, insertable = false)
  private Department department;

  private LocalDate from_date;

  private LocalDate to_date;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentStore that = (DepartmentStore) o;
    return getEmp_no() == that.getEmp_no() && getDeptNo().equals(that.getDeptNo()) && getFrom_date()
        .equals(that.getFrom_date()) && getTo_date().equals(that.getTo_date());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmp_no(), getDeptNo(), getFrom_date(), getTo_date());
  }
}
