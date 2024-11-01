package app.shifter.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.WorkdayDTO;
import app.shifter.interfaces.WorkdayService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workdays")
public class WorkdayController {

    @Autowired
    private WorkdayService workdayService;

    // Endpoint to create or retrieve an existing workday by date
    @PostMapping("/{date}")
    public ResponseEntity<WorkdayDTO> createOrGetWorkday(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.createOrGetWorkday(date);
        return new ResponseEntity<>(workdayDTO, HttpStatus.CREATED);
    }

    // PATCH endpoint to add a shift to an existing workday
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{date}/shift")
    public ResponseEntity<WorkdayDTO> patchWorkdayAddShift(
        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
        @RequestBody Map<String, Long> payload) {
        
        Long shiftId = payload.get("shiftId");
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);
        
        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        WorkdayDTO updatedWorkday = workdayService.addShiftToWorkdayById(workdayDTO, shiftId);
        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    // Endpoint to get a workday by a specific date

    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{date}")
    public ResponseEntity<WorkdayDTO> getWorkdayByDate(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);
        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workdayDTO, HttpStatus.OK);
    }

    // Endpoint to get all workdays
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<WorkdayDTO> getAllWorkdays() {
        return workdayService.getAllWorkdays();
    }
}
