package com.rocktech.employeemvc.service;

import com.rocktech.employeemvc.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);

    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
