package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.DTOs.EmployeeDTO;
import app.shifter.domain.Employee;
import app.shifter.domain.Shift;
import app.shifter.mappers.EmployeeMapper;
import app.shifter.repositories.EmployeeRepository;
import app.shifter.repositories.ShiftRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ShiftRepository shiftRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .map(employeeMapper::employeeToEmployeeDTO)
                .orElse(null);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        if (employeeRepository.existsById(id)) {

            Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
            employee.setEmployeeId(id);

            Employee updatedEmployee = employeeRepository.save(employee);
            return employeeMapper.employeeToEmployeeDTO(updatedEmployee);
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            List<Shift> shifts = shiftRepository.findByEmployee_EmployeeId(id);
            for (Shift shift : shifts) {
                shift.setEmployee(null);
                shiftRepository.save(shift);
            }
            employeeRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public EmployeeDTO getEmployeeByFullName(String firstName, String lastName) {

        return employeeMapper.employeeToEmployeeDTO(
                employeeRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    public EmployeeDTO patchEmployee(Long employeeId, Map<String, Object> updates) {

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

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.employeeToEmployeeDTO(updatedEmployee);
    }
}
