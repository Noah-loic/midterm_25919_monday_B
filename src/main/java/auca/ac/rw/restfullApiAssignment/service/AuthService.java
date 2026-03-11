package auca.ac.rw.restfullApiAssignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.restfullApiAssignment.modal.*;
import auca.ac.rw.restfullApiAssignment.repository.*;
import java.time.LocalDateTime;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    public String register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Username already exists";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already exists";
        }
        userRepository.save(user);
        return "User registered successfully";
    }
    
    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password).orElse(null);
        if (user != null && user.getIsActive()) {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            return user;
        }
        return null;
    }
    
    public String changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "User not found";
        if (!user.getPassword().equals(oldPassword)) return "Incorrect old password";
        user.setPassword(newPassword);
        userRepository.save(user);
        return "Password changed successfully";
    }
}
