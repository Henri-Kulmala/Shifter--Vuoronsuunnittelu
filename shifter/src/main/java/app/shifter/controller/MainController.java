package app.shifter.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.ShiftDTO;
import app.shifter.DTOs.WorkdayDTO;
import app.shifter.domain.Workday;
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
    public String indexPage() {
        return "index"; 
    }

    @GetMapping("/shiftplanner/{date}")
    public String getWorkday(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date, Model model) {
    WorkdayDTO workday = workdayService.getWorkdayByDate(date);
    
    if (workday == null) {
        model.addAttribute("error", "No workday available for the selected date.");
        return "shift-planner"; 
    }

    List<ShiftDTO> shifts = workday.getShifts(); 
    List<EmployeeDTO> employees = employeeService.getAllEmployees(); 
    List<String> workstations = List.of("K1", "K2", "K3", "P1", "IP");

    // Create a map to associate workstations with their corresponding shifts
    Map<String, List<ShiftDTO>> shiftsByWorkstation = shifts.stream()
            .collect(Collectors.groupingBy(ShiftDTO::getWorkstation));

    // Debug logs
   //System.out.println("Workday Date: " + workday.getDate());
   //System.out.println("Shifts: " + shifts);
   //System.out.println("Shifts by Workstation: " + shiftsByWorkstation);
   //

    model.addAttribute("workday", workday);
    model.addAttribute("shiftsByWorkstation", shiftsByWorkstation); 
    model.addAttribute("employees", employees); 
    model.addAttribute("dayOfWeek", workdayService.getDayOfWeek(workday.getDate()));
    model.addAttribute("workstations", workstations);
    
    return "shift-planner"; 
}



@GetMapping("/shiftplanner")
public String shiftPlannerPage(@RequestParam(required = false) LocalDate date, Model model) {
    if (date == null) {
        date = LocalDate.now(); // Default to current date if no date provided
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

    


    
   @PostMapping("/workdays")
    public ResponseEntity<WorkdayDTO> createWorkday(@RequestBody Map<String, String> payload) {
    String dateStr = payload.get("date");
    LocalDate date = LocalDate.parse(dateStr); // Assuming the date is in yyyy-MM-dd format

    WorkdayDTO workdayDTO = workdayService.createOrGetWorkday(date); // Create or get the workday

    return new ResponseEntity<>(workdayDTO, HttpStatus.CREATED); // Return the created workday
}
}

