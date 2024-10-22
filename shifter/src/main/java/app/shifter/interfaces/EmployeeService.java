package app.shifter.interfaces;

import java.util.List;
import java.util.Map;


import app.shifter.domain.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeByFullName(String firstName, String lastName);
    Employee patchEmployee(Long employeeId, Map<String, Object> updates);

}
