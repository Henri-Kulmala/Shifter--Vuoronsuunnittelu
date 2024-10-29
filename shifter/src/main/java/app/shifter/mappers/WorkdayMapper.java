package app.shifter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import app.shifter.domain.Workday;
import app.shifter.DTOs.WorkdayDTO;

@Mapper(componentModel = "spring", uses = { ShiftMapper.class })
public interface WorkdayMapper {
    WorkdayMapper INSTANCE = Mappers.getMapper(WorkdayMapper.class);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "shifts", source = "shifts") 
    Workday workdayDTOToWorkday(WorkdayDTO workdayDTO);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "shifts", source = "shifts")
    WorkdayDTO workdayToWorkdayDTO(Workday workday);
}
