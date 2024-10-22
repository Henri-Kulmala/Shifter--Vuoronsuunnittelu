package app.shifter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.domain.Shifts;
import app.shifter.domain.Break;  // Import Break class
import app.shifter.domain.Employee;
import app.shifter.interfaces.ShiftService;
import app.shifter.repositories.ShiftsRepository;
import app.shifter.interfaces.EmployeeService;


@Service
public class ShiftsServiceImpl implements ShiftService {

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Shifts createShift(Shifts shift) {
        return shiftsRepository.save(shift);
    }

    @Override
    public List<Shifts> getAllShifts() {
        return shiftsRepository.findAll();
    }

    @Override
    public Shifts getShiftById(Long shiftId) {
        return shiftsRepository.findById(shiftId).orElse(null);
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
    public void deleteShift(Long shiftId) {
        shiftsRepository.deleteById(shiftId);
    }

    @Override
    public List<Break> calculateBreaks(LocalDateTime startTime, LocalDateTime endTime) {
        List<Break> breaks = new ArrayList<>();
        long hours = java.time.Duration.between(startTime, endTime).toHours();

        if (hours < 6) {
            // 1 coffee break
            breaks.add(new Break("Coffee", startTime.plusHours(3), startTime.plusMinutes(3).plusMinutes(15)));
        } else if (hours >= 6 && hours < 7) {
            // 2 coffee breaks
            breaks.add(new Break("Coffee", startTime.plusHours(3), startTime.plusMinutes(3).plusMinutes(15)));
            breaks.add(new Break("Coffee", startTime.plusHours(5), startTime.plusMinutes(5).plusMinutes(15)));
        } else if (hours >= 7) {
            // 2 coffee breaks + 1 lunch break
            breaks.add(new Break("Coffee", startTime.plusHours(3), startTime.plusMinutes(3).plusMinutes(15)));
            breaks.add(new Break("Coffee", startTime.plusHours(5), startTime.plusMinutes(5).plusMinutes(15)));
            breaks.add(new Break("Lunch", startTime.plusHours(6), startTime.plusMinutes(6).plusMinutes(30)));
        }

        return breaks;
    }



    @Override
    public Shifts patchShift(Long shiftId, Map<String, Object> updates) {
        Optional<Shifts> optionalShift = shiftsRepository.findById(shiftId);
        if (!optionalShift.isPresent()) {
            throw new RuntimeException("Shift not found");
        }

        Shifts existingShift = optionalShift.get();
        
        updates.forEach((key, value) -> {
            switch (key) {
                case "workstation":
                    existingShift.setWorkstation((String) value);
                    break;
                case "shiftName":
                    existingShift.setShiftName((String) value);
                    break;
                case "startTime":
                    existingShift.setStartTime(LocalDateTime.parse((String) value));
                    break;
                case "endTime":
                    existingShift.setEndTime(LocalDateTime.parse((String) value));
                    break;
                case "employee":
                    Map<String, Object> employeeData = (Map<String, Object>) value;
                    Long employeeId = Long.parseLong(employeeData.get("employeeId").toString());
                    Employee employee = employeeService.getEmployeeById(employeeId);
                    if (employee != null) {
                        existingShift.setEmployee(employee);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return shiftsRepository.save(existingShift);
    }
}
