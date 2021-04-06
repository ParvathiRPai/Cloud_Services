package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.Title;
import com.example.smartsalary.backend.entity.TitleId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, TitleId> {

  List<Title> findByTitle(String t);
}
