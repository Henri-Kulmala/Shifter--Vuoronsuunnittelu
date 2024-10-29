package app.shifter.service;

import java.time.LocalDate;
import java.util.Optional;

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
        Optional<Workday> optionalWorkday = workdayRepository.findByDate(date);
        Workday workday = optionalWorkday.orElseGet(() -> new Workday(date));
        return workdayMapper.workdayToWorkdayDTO(workday);
    }

    @Override
    public WorkdayDTO addShiftToWorkday(WorkdayDTO workdayDTO, ShiftDTO shiftDTO) {
        Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
        workday.getShifts().add(shiftMapper.shiftDTOToShift(shiftDTO));
        Workday updatedWorkday = workdayRepository.save(workday);
        return workdayMapper.workdayToWorkdayDTO(updatedWorkday);
    }

    @Override
    public WorkdayDTO addShiftToWorkdayById(WorkdayDTO workdayDTO, Long shiftId) {
    Shift shift = shiftRepository.findById(shiftId)
        .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
    
    Workday workday = workdayMapper.workdayDTOToWorkday(workdayDTO);
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
