package com.example.smartsalary.backend.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "titles")
@IdClass(TitleId.class)
@Builder
public class Title {

  @Id
  private Long emp_no;

  @Id
  private String title;

  @Id
  private LocalDate from_date;

  private LocalDate to_date;
}
