package app.shifter.interfaces;

import java.util.List;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import app.shifter.DTOs.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO user, String password);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    UserDTO getUserByUserName(String userName);
    UserDTO updateUser(Long userId, UserDTO user, String password);
    void deleteUser(Long userId);
    UserDTO patchUser(Long userId, Map<String, Object> updates);
}
