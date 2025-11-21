package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public Optional<User> getUserById(String id);
    public User updateUserById(String id);
    public void deleteUserById(String id);
}
