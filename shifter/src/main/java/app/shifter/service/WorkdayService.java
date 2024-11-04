package app.shifter.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.ShiftIdListDTO;
import app.shifter.mappers.WorkdayMapper;
import app.shifter.repositories.ShiftRepository;
import app.shifter.repositories.WorkdayRepository;
import app.shifter.service.WorkdayService;
import app.shifter.domain.Shift;
import app.shifter.domain.Workday;

@Service
public class WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;

    @Autowired
    private WorkdayMapper workdayMapper;

    @Autowired
    private ShiftRepository shiftRepository;

    


    


public WorkdayDTO createOrGetWorkday(LocalDate date) {
    Workday workday = workdayRepository.findByDate(date)
            .orElseGet(() -> {
                Workday newWorkday = new Workday(date);
                createPredefinedShifts(newWorkday); // Ensure predefined shifts are created
                return workdayRepository.save(newWorkday);
            });
    return workdayMapper.workdayToWorkdayDTO(workday);
}


    private void createPredefinedShifts(Workday workday) {
    // Predefined workstations and shift types
    String[] workstations = {"K1", "K2", "K3", "P1", "IP"};
    String[] shiftTypes = {"Morning", "Middle", "Evening"};
    
    for (String workstation : workstations) {
        for (String shiftType : shiftTypes) {
            Shift shift = new Shift();
            shift.setWorkstation(workstation);
            shift.setShiftName(shiftType + " Shift");
            // Set default start and end times based on shiftType
            if (shiftType.equals("Morning")) {
                shift.setStartTime(LocalTime.of(8, 0));
                shift.setEndTime(LocalTime.of(12, 0));
            } else if (shiftType.equals("Middle")) {
                shift.setStartTime(LocalTime.of(12, 0));
                shift.setEndTime(LocalTime.of(16, 0));
            } else if (shiftType.equals("Evening")) {
                shift.setStartTime(LocalTime.of(16, 0));
                shift.setEndTime(LocalTime.of(20, 0));
            }
            workday.getShifts().add(shift);
        }
    }
}

    public WorkdayDTO getCurrentWorkDay() {
        LocalDate today = LocalDate.now();
        return createOrGetWorkday(today);
    }

    public String getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().name(); 
    }


    public LocalDate getDate(LocalDate date) {
        return date;
    }

    
    public WorkdayDTO patchWorkdayAddShift(WorkdayDTO workdayDTO, ShiftIdListDTO shiftIdListDTO) {
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
        
        for (Long shiftId : shiftIdListDTO.getShiftIds()) {
            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
            
            shift.setWorkday(workday); 
            workday.getShifts().add(shift);
        }
    
        Workday updatedWorkday = workdayRepository.save(workday);
        return workdayMapper.workdayToWorkdayDTO(updatedWorkday);
    }
    



    public List<WorkdayDTO> getAllWorkdays() {
        return workdayRepository.findAll().stream()
                .map(workdayMapper::workdayToWorkdayDTO)
                .collect(Collectors.toList());
    }

    public WorkdayDTO addShiftToWorkdayById(WorkdayDTO workdayDTO, Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
        
        shift.setWorkday(workday);
        workday.getShifts().add(shift);
        
        Workday updatedWorkday = workdayRepository.save(workday);
        return workdayMapper.workdayToWorkdayDTO(updatedWorkday);
    }

 
    public WorkdayDTO getWorkdayByDate(LocalDate date) {
        return workdayRepository.findByDate(date)
                .map(workdayMapper::workdayToWorkdayDTO)
                .orElse(null);  
    }
}
