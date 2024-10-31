package app.shifter.mappers;

import app.shifter.domain.Break;
import app.shifter.DTOs.BreakDTO;
import app.shifter.domain.Employee;
import app.shifter.DTOs.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { EmployeeMapper.class })
public interface BreakMapper {

    BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);

    List<BreakDTO> breakListToBreakDTOList(List<Break> breaks);

    List<Break> breakDTOListToBreakList(List<BreakDTO> breakDTOs);
}
