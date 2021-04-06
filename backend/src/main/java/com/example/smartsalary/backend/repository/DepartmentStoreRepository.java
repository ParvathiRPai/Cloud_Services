package com.example.smartsalary.backend.repository;

import com.example.smartsalary.backend.entity.DepartmentStore;
import com.example.smartsalary.backend.entity.DepartmentStoreId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentStoreRepository extends
    JpaRepository<DepartmentStore, DepartmentStoreId> {

  List<DepartmentStore> findByDeptNo(String d);
}
