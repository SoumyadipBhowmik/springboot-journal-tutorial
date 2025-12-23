package net.engineering.digest.journal.app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.exception.CreateUserException;
import net.engineering.digest.journal.app.repository.UserRepository;
import net.engineering.digest.journal.app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Slf4j
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
        try {
            user.setPassword(passwordEncoder(user.getPassword()));
            user.setRoles(Collections.singletonList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            throw new CreateUserException("Error while creating user:" + user.getUsername());
        }
    }

    @Override
    public void createAdminUser(User user) {
        try {
            user.setPassword(passwordEncoder(user.getPassword()));
            user.setRoles(Arrays.asList("USER", "ADMIN"));
            userRepository.save(user);
        } catch (Exception e) {
            throw new CreateUserException("Error while creating admin user" + user.getUsername());
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
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
