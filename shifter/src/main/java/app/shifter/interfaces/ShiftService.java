package app.shifter.interfaces;

import java.util.List;

import java.util.Map;

import app.shifter.domain.Break;
import app.shifter.domain.Shifts;
import java.time.LocalDateTime;

public interface ShiftService {
    Shifts createShift(Shifts shift);
    List<Shifts> getAllShifts();
    Shifts getShiftById(Long shiftId);
    Shifts updateShifts(Long shiftId, Shifts shift);
    void deleteShift(Long shiftId);
    Shifts patchShift(Long shiftId, Map<String, Object> updates);
    List<Break> calculateBreaks(LocalDateTime startTime, LocalDateTime endTime);


}
