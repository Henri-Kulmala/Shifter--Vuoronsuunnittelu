package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.shifter.domain.Employee;
import app.shifter.domain.User;
import app.shifter.interfaces.EmployeeService;
import app.shifter.interfaces.UserService;
import app.shifter.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
    
    @Override
    public User updateUser(Long userId, User user) {
        if (userRepository.existsById(userId)) {
        user.setUserId(userId);
        return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

     @Override
    public User patchUser(Long userId, Map<String, Object> updates) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User existingUser = optionalUser.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    existingUser.setUsername((String) value);
                    break;
                case "passwordHash":
                    existingUser.setPasswordHash((String) value);
                    break;
                case "admin":
                    existingUser.setAdmin((Boolean) value);
                    break;
                case "employee":
                    Map<String, Object> employeeData = (Map<String, Object>) value;
                    Long employeeId = Long.parseLong(employeeData.get("employeeId").toString());
                    Employee employee = employeeService.getEmployeeById(employeeId);
                    if (employee != null) {
                        existingUser.setEmployee(employee);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return userRepository.save(existingUser);
    }



}

