package app.shifter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.shifter.domain.User;
import app.shifter.interfaces.UserService;
import app.shifter.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        return userRepository.findByUserName(userName);
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



}

