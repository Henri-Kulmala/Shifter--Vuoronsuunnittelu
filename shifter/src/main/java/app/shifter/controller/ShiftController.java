package app.shifter.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.ShiftDTO;
import app.shifter.interfaces.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {


    // Tuodaan service-luokasta tarvittavat toiminnallisuudet endpointteja varten
    
    @Autowired
    private ShiftService shiftService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ShiftDTO createShift(@RequestBody ShiftDTO shift) {
        return shiftService.createShift(shift);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<ShiftDTO> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ShiftDTO getShiftById(@PathVariable Long id) {
        return shiftService.getShiftById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ShiftDTO updateShifts(@PathVariable Long id, ShiftDTO shift) {
        return shiftService.updateShifts(id, shift);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ShiftDTO patchShift(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    return shiftService.patchShift(id, updates);
}
}
