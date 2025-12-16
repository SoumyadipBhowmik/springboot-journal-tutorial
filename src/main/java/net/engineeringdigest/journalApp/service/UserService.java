package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    void saveUser(User user);

    void createAdminUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(ObjectId id);

    User updateUserByUserName(String userName, User user);

    void deleteUserById(ObjectId id);

    void deleteByUsername(String userName);

    User findByUserName(String userName);
}
