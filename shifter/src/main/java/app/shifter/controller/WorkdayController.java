
package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.WorkdayDTO;
import app.shifter.DTOs.ShiftDTO;
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
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.createOrGetWorkday(date);
        return new ResponseEntity<>(workdayDTO, HttpStatus.CREATED);
    }

    // Endpoint to add a shift to an existing workday
    @PostMapping("/{date}/shifts")
    public ResponseEntity<WorkdayDTO> addShiftToWorkday(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody ShiftDTO shiftDTO) {
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);
        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        WorkdayDTO updatedWorkday = workdayService.addShiftToWorkday(workdayDTO, shiftDTO);
        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    // Endpoint to get a workday by a specific date
    @GetMapping("/{date}")
    public ResponseEntity<WorkdayDTO> getWorkdayByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);
        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workdayDTO, HttpStatus.OK);
    }
}
