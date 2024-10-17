package app.shifter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.shifter.domain.Shifts;
import app.shifter.interfaces.ShiftService;
import app.shifter.repositories.ShiftsRepository;

@Service
public class ShiftsServiceImpl implements ShiftService {

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Override
    public Shifts createShift(Shifts shift) {
        return shiftsRepository.save(shift);
    }

    @Override
    public List<Shifts> getAllShifts() {
        return shiftsRepository.findAll();
    }

     @Override
    public Shifts updateShifts(Long shiftId, Shifts shift) {
        if (shiftsRepository.existsById(shiftId)) {
            shift.setShiftId(shiftId); 
            return shiftsRepository.save(shift);
        }
        return null;
    }

    @Override
    public Shifts getShiftById(Long shiftId) {
        return shiftsRepository.findById(shiftId).orElse(null);
    }

    @Override
    public void deleteShift(Long shiftId) {
        shiftsRepository.deleteById(shiftId);
    }


}
