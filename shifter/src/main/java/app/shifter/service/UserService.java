package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.shifter.DTOs.EmployeeDTO;
import app.shifter.DTOs.UserDTO;
import app.shifter.domain.Employee;
import app.shifter.domain.User;

import app.shifter.mappers.EmployeeMapper;
import app.shifter.mappers.UserMapper;
import app.shifter.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails user = new org.springframework.security.core.userdetails.User(username,
                curruser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }

    public UserDTO createUser(UserDTO userDTO, String password) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);
        String role = userDTO.getRole();
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    public UserDTO getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO, String password) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(userDTO.getUsername());

        if (password != null && !password.isEmpty()) {
            String hashedPassword = passwordEncoder.encode(password);
            existingUser.setPasswordHash(hashedPassword);
        }

        String role = userDTO.getRole();
        existingUser.setRole(role);

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.userToUserDTO(updatedUser);
    }

    public boolean deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
        return true;
    }

    public UserDTO patchUser(Long userId, Map<String, Object> updates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    existingUser.setUsername((String) value);
                    break;
                case "roles":
                    String role = existingUser.getRole();
                    existingUser.setRole(role);
                    break;
                case "employee":
                    @SuppressWarnings("unchecked")
                    Map<String, Object> employeeData = (Map<String, Object>) value;
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
