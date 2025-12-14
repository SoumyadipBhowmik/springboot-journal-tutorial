package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public Optional<User> getUserById(ObjectId id);
    public User updateUserByUserName(String userName, User updatedUser);
    public void deleteUserById(ObjectId id);
    public User findByUserName(String userName);
}
