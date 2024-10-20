package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.shifter.domain.Breaks;
import app.shifter.interfaces.BreakService;

import java.util.List;

@RestController
@RequestMapping("/api/breaks")
public class BreakController {

    @Autowired
    private BreakService breakService;

    @PostMapping
    public Breaks createBreak(@RequestBody Breaks breaks) {
        return breakService.createBreak(breaks);
    }

    @GetMapping
    public List<Breaks> getAllBreaks() {
        return breakService.getAllBreaks();
    }

    @GetMapping("/{id}")
    public Breaks getBreaksById(@PathVariable Long id) {
        return breakService.getBreaksById(id);
    }

    @GetMapping("/type")
    public List<Breaks> getBreaksByBreakType(@RequestParam(required = false) String breakType) {
        if (breakType != null && !breakType.isEmpty()) {
            return breakService.findByBreakType(breakType);
        } else {
            return breakService.getAllBreaks();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBreak(@PathVariable Long id) {
        breakService.deleteBreaks(id);
    }
}
