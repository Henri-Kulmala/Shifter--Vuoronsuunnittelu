package app.shifter.mappers;

import app.shifter.domain.Break;
import app.shifter.DTOs.BreakDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", uses = { EmployeeMapper.class })
public interface BreakMapper {

    BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);

    List<BreakDTO> breakListToBreakDTOList(List<Break> breaks);

    List<Break> breakDTOListToBreakList(List<BreakDTO> breakDTOs);
}
