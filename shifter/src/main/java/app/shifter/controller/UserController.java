package app.shifter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.shifter.domain.Employee;
import app.shifter.domain.User;
import app.shifter.service.EmployeeServiceImpl;
import app.shifter.interfaces.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping
    public User createUser(@RequestBody User user) {
    
    if (user.getEmployee() != null && user.getEmployee().getEmployeeId() != null) {
        Employee employee = employeeService.getEmployeeById(user.getEmployee().getEmployeeId());
        if (employee != null) {
            user.setEmployee(employee); 
        } else {
            throw new RuntimeException("Employee not found"); 
        }
     }
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.patchUser(id, updates);
    }

}
