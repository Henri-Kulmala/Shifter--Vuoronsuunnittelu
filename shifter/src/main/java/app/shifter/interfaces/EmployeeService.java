package app.shifter.interfaces;

import java.util.List;

import app.shifter.domain.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeByFullName(String fullName);

}
