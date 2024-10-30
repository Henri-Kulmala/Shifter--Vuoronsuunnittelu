package app.shifter.interfaces;

import java.util.List;

import java.util.Map;

import app.shifter.DTOs.BreakDTO;
import app.shifter.DTOs.ShiftDTO;
import java.time.LocalTime;

public interface ShiftService {
    ShiftDTO createShift(ShiftDTO shift);
    List<ShiftDTO> getAllShifts();
    ShiftDTO getShiftById(Long shiftId);
    ShiftDTO updateShifts(Long shiftId, ShiftDTO shift);
    void deleteShift(Long shiftId);
    ShiftDTO patchShift(Long shiftId, Map<String, Object> updates);
    List<BreakDTO> calculateBreaks(LocalTime startTime, LocalTime endTime);


}
