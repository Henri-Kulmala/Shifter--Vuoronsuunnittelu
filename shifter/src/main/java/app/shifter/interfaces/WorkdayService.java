package app.shifter.interfaces;

import java.time.LocalDate;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.ShiftDTO;

public interface WorkdayService {

    WorkdayDTO createOrGetWorkday(LocalDate date);
    WorkdayDTO addShiftToWorkday(WorkdayDTO workday, ShiftDTO shift);
    WorkdayDTO getWorkdayByDate(LocalDate date);
    WorkdayDTO addShiftToWorkdayById(WorkdayDTO workdayDTO, Long shiftId);

}
