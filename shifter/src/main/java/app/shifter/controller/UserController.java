package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.UserDTO;
import app.shifter.interfaces.EmployeeService;
import app.shifter.interfaces.UserService;

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
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {

        // Tarkistetaan, että haettu Employee-DTO on täytetty
        if (userDTO.getEmployee() != null && userDTO.getEmployee().getEmployeeId() != null) {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(userDTO.getEmployee().getEmployeeId());
            if (employeeDTO != null) {
                userDTO.setEmployee(employeeDTO);
            } else {
                throw new RuntimeException("Employee not found");
            }
        }
        return userService.createUser(userDTO, userDTO.getPassword()); 
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO, userDTO.getPassword()); // Salasanan encoodaaminen tapahtuu DTO-luokassa
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

 
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public UserDTO patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.patchUser(id, updates);
    }

}

