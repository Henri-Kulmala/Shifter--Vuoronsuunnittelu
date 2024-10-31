package app.shifter.service;

import java.time.LocalTime;
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
import app.shifter.domain.Break;
import app.shifter.domain.Employee;
import app.shifter.domain.Shift;
import app.shifter.exceptionHandling.Exceptions.NotQualifiedException;
import app.shifter.exceptionHandling.Exceptions.OutOfWorkingHoursException;
import app.shifter.exceptionHandling.Exceptions.ShiftConflictException;
import app.shifter.interfaces.ShiftService;
import app.shifter.mappers.BreakMapper;
import app.shifter.mappers.EmployeeMapper;
import app.shifter.mappers.ShiftMapper;
import app.shifter.repositories.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        Long employeeId = shiftDTO.getEmployee().getEmployeeId();
    
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
    
        validateQualification(employee, shiftDTO.getWorkstation());
    
        Shift shift = shiftMapper.shiftDTOToShift(shiftDTO);
        shift.setEmployee(employee);
    
        // Calculate and set initial breaks
        List<BreakDTO> calculatedBreaks = calculateBreaks(shiftDTO.getStartTime(), shiftDTO.getEndTime());
        shift.setBreaks(breakMapper.breakDTOListToBreakList(calculatedBreaks));
    
        Shift savedShift = shiftRepository.save(shift);
    
        return shiftMapper.shiftToShiftDTO(savedShift);
    }

    private void validateQualification(Employee employee, String workstation) {
        Boolean qualification = employee.getQualification();
        if ((workstation.equals("P1") || workstation.equals("P2")) && (qualification == null || !qualification)) {
            throw new NotQualifiedException("This employee is not qualified to work on this workstation.");
        }
    }

    private void validateUniqueShift(ShiftDTO shiftDTO) {
        boolean shiftExists = shiftRepository.existsByWorkstationAndStartTimeAndEndTime(
            shiftDTO.getWorkstation(), shiftDTO.getStartTime(), shiftDTO.getEndTime());
        if (shiftExists) {
            throw new ShiftConflictException("A shift already exists with the same workstation and time.");
        }
    }

    private void validateCoverageLimits(Shift shift, LocalTime breakEndTime) {
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
    public List<BreakDTO> calculateBreaks(LocalTime startTime, LocalTime endTime) {
        List<BreakDTO> breaks = new ArrayList<>();
        long hours = java.time.Duration.between(startTime, endTime).toHours();

        if (hours < 6) {
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(3), startTime.plusHours(3).plusMinutes(15)));
        } else if (hours >= 6 && hours < 7) {
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(3), startTime.plusHours(3).plusMinutes(15)));
            breaks.add(new BreakDTO("Coffee", startTime.plusHours(5), startTime.plusHours(5).plusMinutes(15)));
        } else if (hours >= 7) {
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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        updates.forEach((key, value) -> {
            switch (key) {
                case "workstation":
                    existingShift.setWorkstation((String) value);
                    break;
                case "shiftName":
                    existingShift.setShiftName((String) value);
                    break;
                case "startTime":
                    existingShift.setStartTime(LocalTime.parse((String) value, timeFormatter));
                    break;
                case "endTime":
                    existingShift.setEndTime(LocalTime.parse((String) value, timeFormatter));
                    break;
                case "breaks":
                    @SuppressWarnings("unchecked") List<Map<String, Object>> breaksList = (List<Map<String, Object>>) value;
                    List<BreakDTO> updatedBreaks = new ArrayList<>();

                    for (Map<String, Object> breakData : breaksList) {
                        BreakDTO newBreak = new BreakDTO();
                        newBreak.setBreakType((String) breakData.get("breakType"));
                        newBreak.setBreakStart(LocalTime.parse((String) breakData.get("breakStart"), timeFormatter));
                        newBreak.setBreakEnd(LocalTime.parse((String) breakData.get("breakEnd"), timeFormatter));

                        // Set breakCoverEmployee based on provided ID
                        if (breakData.containsKey("breakCoverEmployee")) {
                            Long breakCoverEmployeeId = Long.parseLong(breakData.get("breakCoverEmployee").toString());
                            Employee breakCoverEmployee = employeeRepository.findById(breakCoverEmployeeId)
                                .orElseThrow(() -> new RuntimeException("Cover employee not found"));
                            newBreak.setBreakCoverEmployee(breakCoverEmployee.getFullName());
                        }

                        updatedBreaks.add(newBreak);
                    }

                    // Map the updated breaks to the shift
                    existingShift.setBreaks(breakMapper.breakDTOListToBreakList(updatedBreaks));
                    break;
                case "employee":
                    @SuppressWarnings("unchecked") Long employeeId = Long.parseLong(((Map<String, Object>) value).get("employeeId").toString());
                    Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
                    existingShift.setEmployee(employee);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        Shift updatedShift = shiftRepository.save(existingShift);
        return shiftMapper.shiftToShiftDTO(updatedShift);
    }

   

}
