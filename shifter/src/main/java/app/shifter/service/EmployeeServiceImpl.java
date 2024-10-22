package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.domain.Employee;
import app.shifter.interfaces.EmployeeService;
import app.shifter.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            employee.setEmployeeId(id); 
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeByFullName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

     @Override
    public Employee patchEmployee(Long employeeId, Map<String, Object> updates) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        Employee existingEmployee = optionalEmployee.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    existingEmployee.setFirstName((String) value);
                    break;
                case "lastName":
                    existingEmployee.setLastName((String) value);
                    break;
                case "qualification":
                    existingEmployee.setQualification((Boolean) value);
                    break;
                case "notes":
                    existingEmployee.setNotes((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return employeeRepository.save(existingEmployee);
    }
}
