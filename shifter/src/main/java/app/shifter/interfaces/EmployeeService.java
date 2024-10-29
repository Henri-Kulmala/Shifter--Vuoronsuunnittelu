package app.shifter.interfaces;

import java.util.List;
import java.util.Map;

import app.shifter.DTOs.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    EmployeeDTO getEmployeeByFullName(String firstName, String lastName);
    EmployeeDTO patchEmployee(Long employeeId, Map<String, Object> updates);
}
