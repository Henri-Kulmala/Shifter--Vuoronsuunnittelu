package app.shifter.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.ShiftDTO;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.service.EmployeeService;
import app.shifter.service.ShiftService;
import app.shifter.service.WorkdayService;

@Controller
public class MainController {

    @Autowired
    private WorkdayService workdayService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
    LocalDate today = LocalDate.now(); 
    model.addAttribute("today", today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return "index";
    }

    @GetMapping("/shiftplanner/{date}")
    public String getWorkday(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date, Model model) {
        WorkdayDTO workday = workdayService.getWorkdayByDate(date);
        
        if (workday == null) {
            workday = workdayService.createOrGetWorkday(date);
            return "shift-planner";
        }
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        List<ShiftDTO> shifts = workday.getShifts();

        Map<String, List<ShiftDTO>> shiftsByWorkstation = shifts.stream()
                .collect(Collectors.groupingBy(
                        ShiftDTO::getWorkstation,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(ShiftDTO::getStartTime))
                                        .collect(Collectors.toList()))));

        List<String> workstations = shifts.stream()
                .map(ShiftDTO::getWorkstation)
                .distinct()
                .collect(Collectors.toList());


        model.addAttribute("workday", workday);
        model.addAttribute("shiftsByWorkstation", shiftsByWorkstation);
        model.addAttribute("shifts", shifts);
        model.addAttribute("employees", employees);
        model.addAttribute("dayOfWeek", workdayService.getDayOfWeek(workday.getDate()));
        model.addAttribute("workstations", workstations);

        return "shift-planner";
    }

    @GetMapping("/shiftplanner")
    public String shiftPlannerPage(@RequestParam(required = false) LocalDate date, Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        WorkdayDTO workday = workdayService.getWorkdayByDate(date);

        if (workday == null) {
            model.addAttribute("error", "No workday available for the selected date.");
            return "shift-planner";
        }

        model.addAttribute("workday", workday);
        model.addAttribute("dayOfWeek", workdayService.getDayOfWeek(workday.getDate()));
        return "shift-planner";
    }

    @PostMapping("/shiftplanner/{date}")
    public RedirectView saveWorkday(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam Map<String, String> shiftEmployeeMap) {

        for (Map.Entry<String, String> entry : shiftEmployeeMap.entrySet()) {
            Long shiftId = Long.parseLong(entry.getKey());
            Long employeeId = Long.parseLong(entry.getValue());
            shiftService.assignEmployee(shiftId, employeeId);
        }

        
        return new RedirectView("/shiftplanner/{date}");
    }

}
