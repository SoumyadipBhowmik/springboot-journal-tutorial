package net.engineeringdigest.journalApp.service.impl;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;


    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
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
            oldUserData.setUsername(updatedUser.getUsername() != null && !updatedUser.equals("") ? updatedUser.getUsername() : oldUserData.getUsername());
            oldUserData.setPassword(updatedUser.getPassword() != null && !updatedUser.equals("") ? updatedUser.getPassword() : oldUserData.getPassword());
            return oldUserData;
        }
        return null;
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
}
