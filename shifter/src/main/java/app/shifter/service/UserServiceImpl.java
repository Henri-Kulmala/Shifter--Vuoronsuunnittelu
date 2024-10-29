package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.UserDTO;
import app.shifter.domain.Employee;
import app.shifter.domain.User;
import app.shifter.interfaces.EmployeeService;
import app.shifter.interfaces.UserService;
import app.shifter.mappers.EmployeeMapper;
import app.shifter.mappers.UserMapper;
import app.shifter.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO, String password) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        // "Hashataan" salasana ennen sen tallentamista
        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);

       
        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

      
        return users.stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

       
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

       
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO, String password) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

       
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setAdmin(userDTO.getAdmin());

      
        if (password != null && !password.isEmpty()) {
            String hashedPassword = passwordEncoder.encode(password);
            existingUser.setPasswordHash(hashedPassword);
        }

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.userToUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @Override
public UserDTO patchUser(Long userId, Map<String, Object> updates) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));


    updates.forEach((key, value) -> {
        switch (key) {
            case "username":
                existingUser.setUsername((String) value);
                break;
            case "admin":
                existingUser.setAdmin((Boolean) value);
                break;
            case "employee":
                @SuppressWarnings("unchecked") Map<String, Object> employeeData = (Map<String, Object>) value;
                Long employeeId = Long.parseLong(employeeData.get("employeeId").toString());
                EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
                if (employeeDTO != null) {
                    Employee employee = EmployeeMapper.INSTANCE.employeeDTOToEmployee(employeeDTO);
                    existingUser.setEmployee(employee);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + key);
        }
    });

      
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.userToUserDTO(updatedUser);
    }
}
