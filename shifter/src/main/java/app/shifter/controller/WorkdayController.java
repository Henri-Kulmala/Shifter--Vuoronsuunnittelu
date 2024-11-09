package app.shifter.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.ShiftIdListDTO;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.service.WorkdayService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workdays")
public class WorkdayController {

    @Autowired
    private WorkdayService workdayService;

    @PostMapping("/{date}")
    public ResponseEntity<WorkdayDTO> createOrGetWorkday(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.createOrGetWorkday(date);
        return new ResponseEntity<>(workdayDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{date}/shift")
    public ResponseEntity<WorkdayDTO> patchWorkdayAddShift(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestBody ShiftIdListDTO payload) {

        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);

        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        WorkdayDTO updatedWorkday = workdayService.patchWorkdayAddShift(workdayDTO, payload);
        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{date}")
    public ResponseEntity<WorkdayDTO> getWorkdayByDate(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);
        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workdayDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<WorkdayDTO>> getAllWorkdays() {
        List<WorkdayDTO> workdays = workdayService.getAllWorkdays();
        return new ResponseEntity<>(workdays, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{date}/shift")
    public ResponseEntity<WorkdayDTO> addShiftsToWorkday(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestBody List<Long> payload) {

        // Retrieve the existing workday by date
        WorkdayDTO workdayDTO = workdayService.getWorkdayByDate(date);

        if (workdayDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the workday with the new list of shifts
        WorkdayDTO updatedWorkday = workdayService.addShiftsToWorkday(workdayDTO, payload);

        return new ResponseEntity<>(updatedWorkday, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkday(@PathVariable Long id) {
        boolean isDeleted = workdayService.deleteWorkday(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
