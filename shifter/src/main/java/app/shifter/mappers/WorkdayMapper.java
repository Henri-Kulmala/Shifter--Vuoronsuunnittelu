package app.shifter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import app.shifter.domain.Workday;
import app.shifter.DTOs.WorkdayDTO;

@Mapper(componentModel = "spring", uses = { ShiftMapper.class })
public interface WorkdayMapper {
    WorkdayMapper INSTANCE = Mappers.getMapper(WorkdayMapper.class);

    @Mapping(target = "workdayId", source = "id")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "dayOfWeek", source = "date.dayOfWeek")
    @Mapping(target = "shifts", source = "shifts")  
    WorkdayDTO workdayToWorkdayDTO(Workday workday);

    @Mapping(target = "id", source = "workdayId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "dayOfWeek", source = "date.dayOfWeek") 
    @Mapping(target = "shifts", source = "shifts")
    Workday workdayDTOToWorkday(WorkdayDTO workdayDTO);
}