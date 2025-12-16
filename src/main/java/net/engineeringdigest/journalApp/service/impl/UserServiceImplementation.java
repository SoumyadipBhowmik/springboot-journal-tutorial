package net.engineeringdigest.journalApp.service.impl;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public User createUser(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setRoles(Collections.singletonList("USER"));
        return userRepository.save(user);
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
    public User updateUserByUserName(String userName, User updatedUser) {
        User oldUserData = userRepository.findByUsername(userName);
        if (oldUserData != null) {
            oldUserData.setUsername(!updatedUser.equals("") ? updatedUser.getUsername() : oldUserData.getUsername());
            oldUserData.setPassword(!updatedUser.equals("") ? updatedUser.getPassword() : oldUserData.getPassword());
            return oldUserData;
        }
        return null;
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
}
