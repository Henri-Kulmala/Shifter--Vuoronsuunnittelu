package app.shifter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.shifter.DTOs.ShiftDTO;
import app.shifter.DTOs.BreakDTO;
import app.shifter.DTOs.EmployeeDTO;
import app.shifter.domain.Shift;
import app.shifter.exceptionHandling.Exceptions.NotQualifiedException;
import app.shifter.exceptionHandling.Exceptions.OutOfWorkingHoursException;
import app.shifter.exceptionHandling.Exceptions.ShiftConflictException;
import app.shifter.interfaces.ShiftService;
import app.shifter.mappers.BreakMapper;
import app.shifter.mappers.EmployeeMapper;
import app.shifter.mappers.ShiftMapper;
import app.shifter.repositories.ShiftRepository;
import app.shifter.interfaces.EmployeeService;


@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ShiftMapper shiftMapper;

    @Autowired
    private BreakMapper breakMapper;

    @Autowired
    private EmployeeMapper employeeMapper;
    

    @Override
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        validateUniqueShift(shiftDTO);  
        validateQualification(shiftDTO, shiftDTO.getWorkstation(), shiftDTO.getEmployee(), shiftDTO.getEmployee().getQualification());
        Shift shift = shiftMapper.shiftDTOToShift(shiftDTO);
        Shift savedShift = shiftRepository.save(shift);

        shift.getBreaks().forEach(breaks -> validateCoverageLimits(shift, breaks.getBreakEnd()));


        return shiftMapper.shiftToShiftDTO(savedShift);
    }


    // Validaatiometodi testaamaan onko työntekijä sallittu tekemään kyseistä työvuoroa
    private void validateQualification(ShiftDTO shiftDTO, String workstation, EmployeeDTO employeeDTO, Boolean qualification) {
    boolean isQualified = shiftRepository.isQualifiedForWorkstation(
        shiftDTO.getWorkstation(), employeeDTO.getQualification());
    
    
    if ((workstation.equals("K1") || workstation.equals("K2")) && !qualification || !isQualified) {
        throw new NotQualifiedException("This employee is not qualified to work on this workstation");
    }
}

    
    private void validateUniqueShift(ShiftDTO shiftDTO) {
        boolean shiftExists = shiftRepository.existsByWorkstationAndStartTimeAndEndTime(
            shiftDTO.getWorkstation(), shiftDTO.getStartTime(), shiftDTO.getEndTime());
        if (shiftExists) {
            throw new ShiftConflictException("A shift already exists with the same workstation and time.");
        }
    }

    private void validateCoverageLimits(Shift shift, LocalDateTime breakEndTime) {
        if (breakEndTime.isAfter(shift.getEndTime())) {
            throw new OutOfWorkingHoursException("Cannot cover breaks outside of working hours.");
        }
    }

    @Override
    public List<ShiftDTO> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(shiftMapper::shiftToShiftDTO) 
                .collect(Collectors.toList());
    }

    @Override
    public ShiftDTO getShiftById(Long shiftId) {
        Optional<Shift> shiftOpt = shiftRepository.findById(shiftId);
        return shiftOpt.map(shiftMapper::shiftToShiftDTO).orElse(null); 
    }

    @Override
    public ShiftDTO updateShifts(Long shiftId, ShiftDTO shiftDTO) {
        if (shiftRepository.existsById(shiftId)) {
            Shift shift = shiftMapper.shiftDTOToShift(shiftDTO); 
            shift.setShiftId(shiftId);
            Shift updatedShift = shiftRepository.save(shift); 
            return shiftMapper.shiftToShiftDTO(updatedShift); 
        }
        return null;
    }

    @Override
    public void deleteShift(Long shiftId) {
        if (shiftRepository.existsById(shiftId)) {
            shiftRepository.deleteById(shiftId);
        } else {
            throw new RuntimeException("Shift not found");
        }
    }

    @Override
    public List<BreakDTO> calculateBreaks(LocalDateTime startTime, LocalDateTime endTime) {
        List<BreakDTO> breaks = new ArrayList<>();
        long hours = java.time.Duration.between(startTime, endTime).toHours();

        if (hours < 6) {
            // Alle 6h vuorossa yksi kahvitauko (12 min)
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(3), startTime.plusHours(3).plusMinutes(15)));
        } else if (hours >= 6 && hours < 7) {
           // Alle 7h mutta yli 6h vuorossa kaksi kahvitaukoa (12 min)
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(3), startTime.plusHours(3).plusMinutes(15)));
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(5), startTime.plusHours(5).plusMinutes(15)));
        } else if (hours >= 7) {
           // Yli 7h vuorossa vähintään kaksi kahvitaukoa (12 min) ja yksi ruokatauko (30 min)
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(3), startTime.plusHours(3).plusMinutes(15)));
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(5), startTime.plusHours(5).plusMinutes(15)));
            breaks.add(new BreakDTO("Lunch", startTime.plusHours(6), startTime.plusHours(6).plusMinutes(30)));
        }

        return breaks;
    }

    @Override
public ShiftDTO patchShift(Long shiftId, Map<String, Object> updates) {
    Optional<Shift> optionalShift = shiftRepository.findById(shiftId);
    if (!optionalShift.isPresent()) {
        throw new RuntimeException("Shift not found");
    }

    Shift existingShift = optionalShift.get();

    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    updates.forEach((key, value) -> {
        switch (key) {
            case "workstation":
                existingShift.setWorkstation((String) value);
                break;
            case "shiftName":
                existingShift.setShiftName((String) value);
                break;
            case "startTime":
                
                existingShift.setStartTime(LocalDateTime.parse((String) value, formatter));
                break;
            case "endTime":
                
                existingShift.setEndTime(LocalDateTime.parse((String) value, formatter));
                break;
            case "breaks":
                @SuppressWarnings("unchecked") List<Map<String, Object>> breaksList = (List<Map<String, Object>>) value;
                List<BreakDTO> updatedBreaks = new ArrayList<>();

                for (Map<String, Object> breakData : breaksList) {
                    BreakDTO newBreak = new BreakDTO();
                    newBreak.setBreakType((String) breakData.get("breakType"));
                   
                    newBreak.setBreakStart(LocalDateTime.parse((String) breakData.get("breakStart"), formatter));
                    newBreak.setBreakEnd(LocalDateTime.parse((String) breakData.get("breakEnd"), formatter));
                    updatedBreaks.add(newBreak);
                }

                existingShift.setBreaks(breakMapper.breakDTOListToBreakList(updatedBreaks));
                break;
            case "employee":
                @SuppressWarnings("unchecked") Map<String, Object> employeeData = (Map<String, Object>) value;
                Long employeeId = Long.parseLong(employeeData.get("employeeId").toString());
                EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
                if (employeeDTO != null) {
                    existingShift.setEmployee(employeeMapper.employeeDTOToEmployee(employeeDTO));
                }
                break;
            case "coveringShift":
                Long coveringShiftId = Long.parseLong(value.toString());
                Optional<Shift> coveringShift = shiftRepository.findById(coveringShiftId);
                if (coveringShift.isPresent()) {
                    existingShift.setCoveringShift(coveringShift.get());
                } else {
                    throw new RuntimeException("Covering shift not found");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + key);
        }
    });

    Shift updatedShift = shiftRepository.save(existingShift);
    return shiftMapper.shiftToShiftDTO(updatedShift);
}

}
