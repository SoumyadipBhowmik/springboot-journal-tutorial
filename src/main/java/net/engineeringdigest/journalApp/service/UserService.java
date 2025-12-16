package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void createUser(User user);

    public void saveUser(User user);

    public void createAdminUser(User user);

    public List<User> getAllUsers();

    public Optional<User> getUserById(ObjectId id);

    public User updateUserByUserName(String userName, User user);

    public void deleteUserById(ObjectId id);

    public void deleteByUsername(String userName);

    public User findByUserName(String userName);
}
