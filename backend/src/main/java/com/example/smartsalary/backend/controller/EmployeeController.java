package com.example.smartsalary.backend.controller;

import com.example.smartsalary.backend.service.EmployeeService;
import com.example.smartsalary.backend.service.dto.EmployeeDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/smartapp")
public class EmployeeController {

  @Autowired
  private EmployeeService service;

  // get all employees
  @GetMapping("/employee")
  public List<EmployeeDto> getAllEmployees() {
    List<EmployeeDto> employee = service.findAll(10);

    if (employee == null) {
      throw new ResourceAccessException("Id not found");
    } else {
      return employee;
    }
  }

  //Create employee rest api
  @PostMapping("/employee")
  public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
    EmployeeDto employee = service.save(employeeDto);
    if (employee == null) {
      throw new ResourceAccessException("save error");
    } else {
      return employee;
    }
  }

  //get employee by id
  @GetMapping("/employee/{id}")
  public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
    EmployeeDto employee = service.findById(id);

    if (employee == null) {
      throw new ResourceAccessException("Id not found");
    } else {
      return ResponseEntity.ok(employee);
    }
  }

  //get employee by id
  @GetMapping("/employee/email/{email}")
  public ResponseEntity<EmployeeDto> getEmployeeByEmail(@PathVariable String email) {
    EmployeeDto employee = service.findByEmail(email);

    if (employee == null) {
      throw new ResourceAccessException("Id not found");
    } else {
      return ResponseEntity.ok(employee);
    }
  }


  // Update Employee
  @PutMapping("/employee/{id}")
  public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
      @RequestBody EmployeeDto toSave) {
    toSave.setEmp_no(id);
    EmployeeDto employee = service.update(toSave);
    if (employee == null) {
      throw new ResourceAccessException("save error");
    } else {
      return ResponseEntity.ok(employee);
    }
  }

  // Delete Employee
  @DeleteMapping("/employee/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {

    if (service.delete(id)) {
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return ResponseEntity.ok(response);
    } else {
      throw new ResourceAccessException("Id not found");
    }
  }

  // Update Salary
  @PutMapping("/salary/{id}")
  public ResponseEntity<EmployeeDto> updateSalary(@PathVariable Long id,
      @RequestBody EmployeeDto salary) {
    salary.setEmp_no(id);
    EmployeeDto employee = service.updateSalary(salary);
    if (employee == null) {
      throw new ResourceAccessException("save error");
    } else {
      return ResponseEntity.ok(employee);
    }
  }

  // get all managers
  @GetMapping("/managers")
  public List<EmployeeDto> getAllManagers() {
    List<EmployeeDto> employee = service.getAllManagers();

    if (employee == null) {
      throw new ResourceAccessException("Id not found");
    } else {
      return employee;
    }
  }

  // get all reports
  @GetMapping("/manager/{id}")
  public List<EmployeeDto> getReports(@PathVariable Long id) {

    List<EmployeeDto> reports = service.getManagerReports(id, 10);
    if (reports == null) {
      throw new ResourceAccessException("error");
    } else {
      return reports;
    }
  }

  // get all reports by email
  @GetMapping("/manager/ebd/{email}")
  public List<EmployeeDto> getReportsByEmail(@PathVariable String email) {

    List<EmployeeDto> reports = service.getManagerReportsByEmail(email, 10);
    if (reports == null) {
      throw new ResourceAccessException("not found");
    } else {
      return reports;
    }
  }

  //TODO : Delete me
  @GetMapping("/test")
  public ResponseEntity<Map<String, Boolean>> runTest() {

    List<EmployeeDto> eList = getAllEmployees();

    log.info("1 " + eList.toString());

    EmployeeDto e = getEmployeeById(eList.get(0).getEmp_no()).getBody();

    log.info("2 " + e);

    e = getEmployeeByEmail(eList.get(0).getEmailid()).getBody();

    log.info("3 " + e);

    List<EmployeeDto> managers = getAllManagers();

    log.info("4 : " + managers.toString());

    List<EmployeeDto> reports = getReports(managers.get(3).getEmp_no());

    log.info("5 : " + reports.toString());

    reports = getReportsByEmail(
        managers.get(3).getEmailid() + "~" + managers.get(3).getBirth_date());

    log.info("6 : " + reports.toString());

    EmployeeDto toCreate = EmployeeDto.builder()
        .first_name("AA")
        .last_name("ZZ")
        .birth_date("2002-01-01")
        .department("Development")
        .gender("M")
        .salary(300000)
        .title("Senior Staff")
        .build();
    log.info("7 : to Create " + toCreate);

    EmployeeDto created = createEmployee(toCreate);

    log.info("7 : Created " + created);

    EmployeeDto toUpdate = EmployeeDto.builder().build();
    toUpdate.setBirth_date(toCreate.getBirth_date());
    toUpdate.setFirst_name("ZZ");
    toUpdate.setLast_name("AA");
    toUpdate.setGender("F");

    log.info("8 : to Update " + toUpdate);

    ResponseEntity<EmployeeDto> updated = updateEmployee(created.getEmp_no(), toUpdate);

    log.info("8 : to Updated " + updated.getBody());

    EmployeeDto toUpdateSalary = EmployeeDto.builder().build();
    toUpdateSalary.setSalary(500000);

    log.info("8 : to Update Salary " + toUpdate);

    ResponseEntity<EmployeeDto> updatedSalary = updateSalary(created.getEmp_no(), toUpdateSalary);
    EmployeeDto toDelete = updatedSalary.getBody();
    log.info("8 : to Updated Salary " + toDelete);

    log.info("9 Delete " + toDelete);

    ResponseEntity res = deleteEmployee(toDelete.getEmp_no());

    log.info("9 Deleted " + res.toString());

    try {
      getEmployeeById(toDelete.getEmp_no());
    } catch (Exception ex) {
      log.info("10 ");
    }

    Map<String, Boolean> response = new HashMap<>();
    response.put("test succeeded", Boolean.TRUE);
    return ResponseEntity.ok(response);

  }
}
