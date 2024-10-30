package app.shifter.interfaces;

import java.time.LocalDate;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.ShiftDTO;
import java.util.List;

public interface WorkdayService {

    WorkdayDTO createOrGetWorkday(LocalDate date);
    WorkdayDTO patchWorkdayAddShift(WorkdayDTO workday, ShiftDTO shift);
    WorkdayDTO getWorkdayByDate(LocalDate date);
    WorkdayDTO addShiftToWorkdayById(WorkdayDTO workdayDTO, Long shiftId);
    List<WorkdayDTO> getAllWorkdays();

}
