package app.shifter.mappers;

import app.shifter.domain.Shift;
import app.shifter.DTOs.ShiftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring", uses = {BreakMapper.class, EmployeeMapper.class})
public interface ShiftMapper {
    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    Shift shiftDTOToShift(ShiftDTO shiftDTO);

    ShiftDTO shiftToShiftDTO(Shift shift);

    List<Shift> shiftDTOListToShiftList(List<ShiftDTO> shiftDTOs);

    List<ShiftDTO> shiftListToShiftDTOList(List<Shift> shifts);
}