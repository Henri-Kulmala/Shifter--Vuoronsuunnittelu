package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.shifter.domain.Shifts;
import app.shifter.interfaces.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftsController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping
    public Shifts createShift(@RequestBody Shifts shift) {
        return shiftService.createShift(shift);
    }

    @GetMapping
    public List<Shifts> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping("/{id}")
    public Shifts getShiftById(@PathVariable Long id) {
        return shiftService.getShiftById(id);
    }

    @PutMapping("/{id}")
    public Shifts updateShifts(@PathVariable Long id, Shifts shift) {
        return shiftService.updateShifts(id, shift);
    }

    @DeleteMapping("/{id}")
    public void deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
    }
}
