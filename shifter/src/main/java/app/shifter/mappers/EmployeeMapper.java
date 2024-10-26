package app.shifter.mappers;

import app.shifter.domain.Employee;
import app.shifter.DTOs.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // Mapping from Entity to DTO
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    // Mapping from DTO to Entity
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}