package app.shifter.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.ShiftDTO;
import app.shifter.service.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShiftDTO> createShift(@RequestBody ShiftDTO shift) {
        ShiftDTO createdShift = shiftService.createShift(shift);
        return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shifts = shiftService.getAllShifts();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getShiftById(@PathVariable Long id) {
        ShiftDTO shift = shiftService.getShiftById(id);
        if (shift != null) {
            return new ResponseEntity<>(shift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ShiftDTO> updateShifts(@PathVariable Long id, @RequestBody ShiftDTO shift) {
        ShiftDTO updatedShift = shiftService.updateShifts(id, shift);
        if (updatedShift != null) {
            return new ResponseEntity<>(updatedShift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        boolean isDeleted = shiftService.deleteShift(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ShiftDTO> patchShift(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ShiftDTO patchedShift = shiftService.patchShift(id, updates);
        if (patchedShift != null) {
            return new ResponseEntity<>(patchedShift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/{employeeId}")
    public ResponseEntity<ShiftDTO> assignEmplyoee(@PathVariable Long id, @PathVariable Long employeeId) {
        ShiftDTO assignedEmplyoee = shiftService.assignEmployee(id, employeeId);
        if (assignedEmplyoee != null) {
            return new ResponseEntity<>(assignedEmplyoee, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
