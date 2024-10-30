package app.shifter.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.ShiftDTO;
import app.shifter.mappers.WorkdayMapper;
import app.shifter.mappers.ShiftMapper;
import app.shifter.repositories.ShiftRepository;
import app.shifter.repositories.WorkdayRepository;
import app.shifter.interfaces.WorkdayService;
import app.shifter.domain.Shift;
import app.shifter.domain.Workday;

@Service
public class WorkdayServiceImpl implements WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;

    @Autowired
    private WorkdayMapper workdayMapper;

    @Autowired
    private ShiftMapper shiftMapper;

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public WorkdayDTO createOrGetWorkday(LocalDate date) {

        Workday workday = workdayRepository.findByDate(date)
                .orElseGet(() -> workdayRepository.save(new Workday(date)));
        return workdayMapper.workdayToWorkdayDTO(workday);
    }

    @Override
    public WorkdayDTO patchWorkdayAddShift(WorkdayDTO workdayDTO, ShiftDTO shiftDTO) {
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
        Shift shift = shiftMapper.shiftDTOToShift(shiftDTO);
        
        shift.setWorkday(workday);
        workday.getShifts().add(shift);

        Workday updatedWorkday = workdayRepository.save(workday);
        return workdayMapper.workdayToWorkdayDTO(updatedWorkday);
    }

    @Override
    public List<WorkdayDTO> getAllWorkdays() {
        return workdayRepository.findAll().stream()
                .map(workdayMapper::workdayToWorkdayDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkdayDTO addShiftToWorkdayById(WorkdayDTO workdayDTO, Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
        
        shift.setWorkday(workday);
        workday.getShifts().add(shift);
        
        Workday updatedWorkday = workdayRepository.save(workday);
        return workdayMapper.workdayToWorkdayDTO(updatedWorkday);
    }

    @Override
    public WorkdayDTO getWorkdayByDate(LocalDate date) {
        return workdayRepository.findByDate(date)
                .map(workdayMapper::workdayToWorkdayDTO)
                .orElse(null);  
    }
}
