package net.engineering.digest.journal.app.service;

import net.engineering.digest.journal.app.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    void saveUser(User user);

    void createAdminUser(User user);

    Page<User> getAllUsers(Pageable pageable);

    Optional<User> getUserById(ObjectId id);

    User updateUserByUserName(String userName, User user);

    void deleteUserById(ObjectId id);

    void deleteByUsername(String userName);

    User findByUserName(String userName);
}
