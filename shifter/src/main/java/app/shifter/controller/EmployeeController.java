package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.interfaces.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    // Tuodaan service-luokasta tarvittavat toiminnallisuudet endpointteja varten
    @Autowired
    private EmployeeService employeeService;


    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.createEmployee(employee);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(id, employee);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public EmployeeDTO patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return employeeService.patchEmployee(id, updates);
    }

}


// HTTPs Status Koodit!