package app.shifter.interfaces;

import java.util.List;

import app.shifter.domain.Shifts;

public interface ShiftService {
    Shifts createShift(Shifts shift);
    List<Shifts> getAllShifts();
    Shifts getShiftById(Long shiftId);
    Shifts updateShifts(Long shiftId, Shifts shift);
    void deleteShift(Long shiftId);

}
