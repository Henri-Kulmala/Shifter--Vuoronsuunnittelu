package app.shifter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import app.shifter.domain.Shift;
import app.shifter.DTOs.ShiftDTO;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    Shift shiftDTOToShift(ShiftDTO shiftDTO);

    ShiftDTO shiftToShiftDTO(Shift shift);
}
