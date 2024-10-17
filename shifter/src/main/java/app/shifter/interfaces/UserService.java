package app.shifter.interfaces;

import java.util.List;

import app.shifter.domain.User;

public interface UserService {

    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    User getUserByUserName(String userName);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
