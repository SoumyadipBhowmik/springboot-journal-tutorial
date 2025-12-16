package net.engineeringdigest.journalApp.service.impl;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public String passwordEncoder(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder(user.getPassword()));
        user.setRoles(Collections.singletonList("USER"));
        userRepository.save(user);
    }

    @Override
    public void createAdminUser(User user) {
        user.setPassword(passwordEncoder(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User updateUserByUserName(String userName, User user) {
        User userInDB = userRepository.findByUsername(userName);
        if (!user.getPassword().isEmpty()) {
            userInDB.setPassword(passwordEncoder(user.getPassword()));
        }
        return userRepository.save(userInDB);
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String userName) {
        userRepository.deleteByUsername(userName);
    }
}
