package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employee) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employee);
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        EmployeeDTO patchedEmployee = employeeService.patchEmployee(id, updates);
        if (patchedEmployee != null) {
            return new ResponseEntity<>(patchedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
