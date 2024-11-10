package app.shifter.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.BreakDTO;
import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.ShiftDTO;
import app.shifter.DTOs.ShiftIdListDTO;
import app.shifter.mappers.BreakMapper;
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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private BreakMapper breakMapper;

    public WorkdayDTO createOrGetWorkday(LocalDate date) {
        Workday workday = workdayRepository.findByDate(date)
                .orElseGet(() -> {
                    Workday newWorkday = new Workday(date);

                    workdayRepository.saveAndFlush(newWorkday);
                    
                    ShiftIdListDTO shiftIdListDTO = new ShiftIdListDTO();
                    List<Long> shiftIds = createPredefinedShifts(newWorkday);

                    
                    shiftIdListDTO.setShiftIds(shiftIds);
                    
                    WorkdayDTO newWorkdayDTO = workdayMapper.workdayToWorkdayDTO(newWorkday);

                    addShiftsToWorkday(newWorkdayDTO, shiftIds);
    
                    return newWorkday;
                });
        return workdayMapper.workdayToWorkdayDTO(workday);
    }

    public WorkdayDTO saveWorkDay(WorkdayDTO workdayDTO) {

        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);

        Workday savedWorkday = workdayRepository.save(workday);

        return workdayMapper.workdayToWorkdayDTO(savedWorkday);
    }

    private List<Long> createPredefinedShifts(Workday workday) {
    String[] workstations = { "K1", "K2", "K3", "P1", "IP" };
    String[] shiftTypes = { "Aamuvuoro", "Välivuoro", "Iltavuoro" };
    List<Long> shiftIds = new ArrayList<>();

    for (String workstation : workstations) {
        for (String shiftType : shiftTypes) {
            Shift shift = new Shift();
            shift.setWorkstation(workstation);
            shift.setShiftName(shiftType);

            if (shiftType.equals("Aamuvuoro")) {
                shift.setStartTime(LocalTime.of(7, 0));
                shift.setEndTime(LocalTime.of(14, 0));
            } else if (shiftType.equals("Välivuoro")) {
                shift.setStartTime(LocalTime.of(11, 0));
                shift.setEndTime(LocalTime.of(19, 0));
            } else if (shiftType.equals("Iltavuoro")) {
                shift.setStartTime(LocalTime.of(15, 0));
                shift.setEndTime(LocalTime.of(23, 0));
            }

            
            shift.setWorkday(workday);
            List<BreakDTO> calculatedBreaks = shiftService.calculateBreaks(shift.getStartTime(), shift.getEndTime());
            shift.setBreaks(breakMapper.breakDTOListToBreakList(calculatedBreaks));
            shift = shiftRepository.save(shift);  
            shiftIds.add(shift.getShiftId());     
        }
    }
    return shiftIds;
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

    

    public WorkdayDTO addShiftsToWorkday(WorkdayDTO workdayDTO, List<Long> shiftIds) {
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);

        if (shiftIds == null || shiftIds.isEmpty()) {
            throw new IllegalArgumentException("Shift IDs list is empty or null.");
        }

        workday.getShifts().clear();

        for (Long shiftId : shiftIds) {
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

    public void assignEmployeeToShift(WorkdayDTO workdayDTO, Long shiftId, Long employeeId) {
        ShiftDTO shift = workdayDTO.getShifts().stream()
                .filter(s -> s.getShiftId().equals(shiftId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));

        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        shift.setEmployee(employee);
    }

    public boolean deleteWorkday(Long workdayId) {
        if (workdayRepository.existsById(workdayId)) {
            workdayRepository.deleteById(workdayId);
        } else { 
            throw new RuntimeException("Workday not found");
    }
        return false;

    }

}
