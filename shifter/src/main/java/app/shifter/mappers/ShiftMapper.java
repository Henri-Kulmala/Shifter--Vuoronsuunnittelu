package app.shifter.mappers;

import app.shifter.domain.Shift;
import app.shifter.DTOs.ShiftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    // Mapping from Entity to DTO
    ShiftDTO shiftToShiftDTO(Shift shift);

    // Mapping from DTO to Entity
    Shift shiftDTOToShift(ShiftDTO shiftDTO);
}
