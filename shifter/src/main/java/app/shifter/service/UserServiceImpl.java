package app.shifter.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceImpl implements UserService, UserDetailsService {

    
    private final UserRepository userRepository;
    
        @Autowired
        public UserServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;
    }
    
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User curruser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails user = new org.springframework.security.core.userdetails.User(username,
        curruser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }

    
    @Override
    public UserDTO createUser(UserDTO userDTO, String password) {
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        // Hash the password before storing
        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);

        // Set roles by looking them up in the database
        String role = userDTO.getRole();
        user.setRole(role);

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

        if (password != null && !password.isEmpty()) {
            String hashedPassword = passwordEncoder.encode(password);
            existingUser.setPasswordHash(hashedPassword);
        }

        String role = userDTO.getRole();
        existingUser.setRole(role);

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
                case "roles":
                String role = existingUser.getRole();
                existingUser.setRole(role);
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
