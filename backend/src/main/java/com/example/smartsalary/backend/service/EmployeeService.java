package com.example.smartsalary.backend.service;

import com.example.smartsalary.backend.entity.Department;
import com.example.smartsalary.backend.entity.DepartmentStore;
import com.example.smartsalary.backend.entity.DepartmentStore.DepartmentStoreBuilder;
import com.example.smartsalary.backend.entity.Employee;
import com.example.smartsalary.backend.entity.Employee.EmployeeBuilder;
import com.example.smartsalary.backend.entity.Gender;
import com.example.smartsalary.backend.entity.Salary;
import com.example.smartsalary.backend.entity.Salary.SalaryBuilder;
import com.example.smartsalary.backend.entity.Title;
import com.example.smartsalary.backend.entity.Title.TitleBuilder;
import com.example.smartsalary.backend.repository.DepartmentRepository;
import com.example.smartsalary.backend.repository.DepartmentStoreRepository;
import com.example.smartsalary.backend.repository.EmployeeRepository;
import com.example.smartsalary.backend.repository.SalaryRepository;
import com.example.smartsalary.backend.repository.TitleRepository;
import com.example.smartsalary.backend.service.dto.EmployeeDto;
import com.example.smartsalary.backend.service.dto.EmployeeDto.EmployeeDtoBuilder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

  public static LocalDate infinity = LocalDate
      .parse("9999-01-01", DateTimeFormatter.ISO_LOCAL_DATE);
  private final EmployeeRepository employeeRepository;
  private final SalaryRepository salaryRepository;
  private final DepartmentStoreRepository departmentStoreRepository;
  private final DepartmentRepository departmentRepository;
  private final TitleRepository titleRepository;

  public EmployeeService(
      EmployeeRepository employeeRepository,
      SalaryRepository salaryRepository,
      DepartmentStoreRepository departmentStoreRepository,
      DepartmentRepository departmentRepository,
      TitleRepository titleRepository) {
    this.employeeRepository = employeeRepository;
    this.salaryRepository = salaryRepository;
    this.departmentStoreRepository = departmentStoreRepository;
    this.departmentRepository = departmentRepository;
    this.titleRepository = titleRepository;
  }

  private static EmployeeDto getEmployeeDto(Employee e) {
    EmployeeDtoBuilder builder = EmployeeDto
        .builder();

    builder.emp_no(e.getEmpNo());
    builder.birth_date(e.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    builder.first_name(e.getFname());
    builder.last_name(e.getLname());
    builder.gender(e.getGender().name());
    builder.emailid(
        e.getFname() + "_" + e.getLname() + "_" + e.getBirthDate().getYear() + "@corp.example.com");
    builder.hire_date(e.getHire_date().format(DateTimeFormatter.ISO_LOCAL_DATE));

    Optional<Salary> currentSalary = e.getSalary()
        .stream()
        .max(Comparator.comparing(Salary::getTo_date));

    builder.salary(currentSalary.map(Salary::getSalary).orElse(0L));

    Optional<DepartmentStore> currentDepartment = e.getDepartmentStores()
        .stream()
        .max(Comparator.comparing(DepartmentStore::getTo_date));

    builder.department(
        currentDepartment.map(departmentStore -> departmentStore.getDepartment().getDname())
            .orElse(null));
    builder.manager(currentDepartment
        .map(m -> m.getDepartment().getManager().getFname() + " " + m.getDepartment()
            .getManager().getLname())
        .orElse(null));

    Optional<Title> currentTitle = e
        .getTitles()
        .stream()
        .max(Comparator.comparing(Title::getTo_date));

    if (currentTitle.isPresent()) {

      Title currentT = currentTitle.get();

      //log.info("currentT : " + currentT + " " + currentT.getTo_date().isEqual(infinity));
      builder.title(currentT.getTitle());

      if (currentT.getTo_date().isEqual(infinity)) {
        builder.currentEmployee(true);
      }
    }

    return builder.build();
  }

  public EmployeeDto findById(long id) {

    Optional<Employee> employee = employeeRepository.findById(id);

    if (employee.isPresent()) {

      Employee e = employee.get();
      log.info("findById : " + e);
      return getEmployeeDto(e);
    }

    return null;
  }

  public EmployeeDto findByEmail(String email) {

    if (email != null && email.contains("@") && email.contains("_")) {

      String[] name = email.split("@")[0].split("_");

      if (name.length == 3) {
        log.info("findByEmail : " + email + " name : " + Arrays.toString(name));
        List<Employee> employee =
            employeeRepository.findByFnameIgnoreCaseAndLnameIgnoreCase(name[0], name[1]);
        if (!employee.isEmpty()) {

          for (Employee e : employee) {
            if (e.getBirthDate().getYear() == Integer.parseInt(name[2])) {
              log.info("findById : " + e);
              return getEmployeeDto(e);
            }
          }
        }
      }
    }

    return null;
  }

  public List<EmployeeDto> findAll(int limit) {

    Page<Employee> employee = employeeRepository.findAll(PageRequest.of(0, limit));
    if (employee.hasContent()) {

      return employee.stream().map(EmployeeService::getEmployeeDto).collect(Collectors.toList());
    }

    return null;
  }

  public boolean delete(long id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    if (employee.isPresent()) {

      employeeRepository.deleteById(employee.get().getEmpNo());

      return true;
    }

    return false;
  }

  public EmployeeDto save(EmployeeDto employeeDto) {

    //validate min info
    if (employeeDto.getDepartment() != null
        && employeeDto.getFirst_name() != null
        && employeeDto.getLast_name() != null
        && employeeDto.getSalary() > 0L
        && employeeDto.getBirth_date() != null
        && employeeDto.getGender() != null
        && employeeDto.getTitle() != null) {

      LocalDate today = LocalDate.now(ZoneId.of(ZoneOffset.UTC.getId()));

      LocalDate birthDate = null;
      try {
        birthDate = LocalDate.parse(employeeDto.getBirth_date(), DateTimeFormatter.ISO_LOCAL_DATE);
      } catch (DateTimeParseException e) {
        log.error("", e);
        return null;
      }

      //check if exists
      Optional<Employee> employee = employeeRepository
          .findByFnameIgnoreCaseAndLnameIgnoreCaseAndBirthDate(employeeDto.getFirst_name(),
              employeeDto.getLast_name(), birthDate);
      if (employee.isPresent()) {
        log.error("Already present");
        return null;
      }

      //check department is valid
      Optional<Department> dept = departmentRepository.findByDname(employeeDto.getDepartment());
      if (dept.isEmpty()) {
        return null;
      }

      List<Title> title = titleRepository.findByTitle(employeeDto.getTitle());
      if (title.isEmpty()) {
        return null;
      }

      EmployeeBuilder toSave = Employee.builder();

      try {
        toSave.birthDate(
            LocalDate.parse(employeeDto.getBirth_date(), DateTimeFormatter.ISO_LOCAL_DATE));
      } catch (DateTimeParseException e) {
        log.error("date error", e);
        return null;
      }

      toSave.fname(employeeDto.getFirst_name());
      toSave.lname(employeeDto.getLast_name());
      toSave.gender(Gender.valueOf(employeeDto.getGender()));
      toSave.hire_date(today);

      long max = employeeRepository.getMaxEmpNo();
      log.info("Max emp " + max);
      toSave.empNo(max + 1);

      Employee saved = employeeRepository.saveAndFlush(toSave.build());
      log.info("Saved : " + saved);

      //add other values

      DepartmentStoreBuilder departmentStore = DepartmentStore.builder();
      departmentStore.emp_no(saved.getEmpNo());
      departmentStore.deptNo(dept.get().getDeptNo());
      departmentStore.from_date(today);
      departmentStore.to_date(infinity);
      departmentStore.department(dept.get());

      DepartmentStore savedStore = departmentStoreRepository.saveAndFlush(departmentStore.build());
      log.info("Saved : " + savedStore);

      TitleBuilder titleBuilder = Title.builder();
      titleBuilder.title(employeeDto.getTitle());
      titleBuilder.emp_no(saved.getEmpNo());
      titleBuilder.from_date(today);
      titleBuilder.to_date(infinity);

      Title savedTitle = titleRepository.saveAndFlush(titleBuilder.build());
      log.info("Saved : " + savedTitle);

      SalaryBuilder salaryBuilder = Salary.builder();
      salaryBuilder.salary(employeeDto.getSalary());
      salaryBuilder.emp_no(saved.getEmpNo());
      salaryBuilder.from_date(today);
      salaryBuilder.to_date(infinity);

      Salary savedSalary = salaryRepository.saveAndFlush(salaryBuilder.build());
      log.info("Saved : " + savedSalary);

      saved.setDepartmentStores(Set.of(savedStore));
      saved.setTitles(Set.of(savedTitle));
      saved.setSalary(Set.of(savedSalary));

      return getEmployeeDto(saved);

    } else {
      return null;
    }
  }

  public EmployeeDto update(EmployeeDto employeeDto) {

    //validate min info
    if (employeeDto.getFirst_name() != null
        && employeeDto.getLast_name() != null
        && employeeDto.getBirth_date() != null
        && employeeDto.getGender() != null) {

      //check if exists
      Optional<Employee> employee = employeeRepository.findById(employeeDto.getEmp_no());
      if (employee.isEmpty()) {
        log.error("does not exist " + employeeDto);
        return null;
      }

      Employee existing = employee.get();
      log.info("existing : " + existing);

      Employee toSave = Employee.builder().build();

      try {
        toSave.setBirthDate(
            LocalDate.parse(employeeDto.getBirth_date(), DateTimeFormatter.ISO_LOCAL_DATE));
      } catch (DateTimeParseException e) {
        log.error("date error", e);
        return null;
      }

      Set<Salary> s = existing.getSalary();
      Set<Title> t = existing.getTitles();
      Set<DepartmentStore> d = existing.getDepartmentStores();

      toSave.setFname(employeeDto.getFirst_name());
      toSave.setLname(employeeDto.getLast_name());
      toSave.setGender(Gender.valueOf(employeeDto.getGender()));
      toSave.setHire_date(existing.getHire_date());
      toSave.setEmpNo(existing.getEmpNo());

      log.info("toSave : " + toSave);

      Employee saved = employeeRepository.save(toSave);

      log.info("Saved : " + saved);

      saved.setSalary(s);
      saved.setTitles(t);
      saved.setDepartmentStores(d);
      log.info("decorated : " + saved);
      return getEmployeeDto(saved);

    }
    log.error("invalid req " + employeeDto);
    return null;
  }

  public EmployeeDto updateSalary(EmployeeDto newSalary) {
    if (newSalary.getSalary() > 0L) {

      Optional<Employee> employee = employeeRepository.findById(newSalary.getEmp_no());

      if (employee.isPresent()) {
        Employee employeeV = employee.get();
        Set<Salary> salary = employeeV.getSalary();
        log.info("Found : " + salary);

        LocalDate today = LocalDate.now(ZoneId.of(ZoneOffset.UTC.getId()));

        Optional<Salary> latestSalary = salary.stream()
            .max(Comparator.comparing(Salary::getTo_date));

        if (latestSalary.isPresent()) {

          Salary latestSalaryV = latestSalary.get();

          if (latestSalaryV.getTo_date().isEqual(infinity)) {

            for (Salary s : salary) {
              if (s.equals(latestSalaryV)) {
                s.setTo_date(today);
                salaryRepository.save(s);
              }
            }

            Salary ns = Salary.builder()
                .salary(newSalary.getSalary())
                .from_date(today)
                .to_date(infinity)
                .emp_no(newSalary.getEmp_no())
                .build();
            log.info("toSave : " + ns);

            Salary savedSal = salaryRepository.save(ns);
            log.info("Saved : " + savedSal);

            employeeV.setSalary(Set.of(ns));
            return getEmployeeDto(employeeV);
          }
        }
      }
    }
    return null;
  }

  public List<EmployeeDto> getAllManagers() {
    List<Long> managerList = departmentRepository.findAll()
        .stream()
        .map(Department::getManager)
        .map(Employee::getEmpNo)
        .collect(Collectors.toList());

    return employeeRepository.findAllById(managerList)
        .stream()
        .map(EmployeeService::getEmployeeDto)
        .collect(Collectors.toList());
  }

  public List<EmployeeDto> getManagerReports(long managerEmpId, int limit) {

    Optional<Employee> e = employeeRepository.findById(managerEmpId);

    if (e.isPresent()) {

      Employee employee = e.get();
      Optional<DepartmentStore> currentManagerD = employee.getDepartmentStores()
          .stream()
          .filter(s -> s.getTo_date().isEqual(infinity))
          .findFirst();

      if (currentManagerD.isPresent()) {

        DepartmentStore departmentStore = currentManagerD.get();

        boolean isManager = departmentStore.getDepartment().getManager().getEmpNo()
            .equals(employee.getEmpNo());

        if (isManager) {
          List<DepartmentStore> departmentStores = departmentStoreRepository.findByDeptNo(
              departmentStore.getDeptNo());

          Map<Long, List<DepartmentStore>> groupedByEmployee =
              departmentStores
                  .stream()
                  .collect(Collectors.groupingBy(DepartmentStore::getEmp_no));

          if (!groupedByEmployee.isEmpty()) {
            Set<Long> currentReports = new HashSet<>();

            for (Map.Entry<Long, List<DepartmentStore>> entry : groupedByEmployee.entrySet()) {
              Optional<DepartmentStore> latestDepartment = entry.getValue()
                  .stream()
                  .max(Comparator.comparing(DepartmentStore::getTo_date));

              if (latestDepartment.isPresent() && latestDepartment.get().getTo_date()
                  .isEqual(infinity)) {
                currentReports.add(entry.getKey());
              }

              if (currentReports.size() == limit) {
                break;
              }
            }

            if (!currentReports.isEmpty()) {
              return employeeRepository.findAllById(currentReports)
                  .stream()
                  .map(EmployeeService::getEmployeeDto)
                  .collect(Collectors.toList());
            }
          } //no reports
        } // not a manager
      } // no current department
    } // not valid employee
    return null;
  }

  public List<EmployeeDto> getManagerReportsByEmail(String email, int limit) {

    EmployeeDto employeeDto = findByEmail(email);

    if (employeeDto != null) {
      return getManagerReports(employeeDto.getEmp_no(), limit);
    }

    return null;
  }
}
