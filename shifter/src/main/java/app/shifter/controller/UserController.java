package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.UserDTO;
import app.shifter.service.EmployeeService;
import app.shifter.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getEmployee() != null && userDTO.getEmployee().getEmployeeId() != null) {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(userDTO.getEmployee().getEmployeeId());
            if (employeeDTO != null) {
                userDTO.setEmployee(employeeDTO);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            }
        }
        UserDTO createdUser = userService.createUser(userDTO, userDTO.getPassword());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO, userDTO.getPassword());
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserDTO patchedUser = userService.patchUser(id, updates);
        if (patchedUser != null) {
            return new ResponseEntity<>(patchedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }
}
