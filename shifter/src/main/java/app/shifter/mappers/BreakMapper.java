package app.shifter.mappers;

import app.shifter.domain.Break;
import app.shifter.DTOs.BreakDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BreakMapper {

    BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);

    Break breakDTOToBreak(BreakDTO breakDTO);

    List<Break> breakDTOListToBreakList(List<BreakDTO> breakDTOs);

    BreakDTO breakToBreakDTO(Break breaks);

    List<BreakDTO> breakListToBreakDTOList(List<Break> breaks);
}