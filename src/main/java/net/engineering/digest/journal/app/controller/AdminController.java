package net.engineering.digest.journal.app.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return (!allUsers.isEmpty())
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<User> createAdminUser(@RequestBody User user) {
        try {
            userService.createAdminUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cache")
    public ResponseEntity<Void> clearAppCache() {
        try {
            appCache.init();
            log.info("Cache cleared and reinitialized successfully");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't initialize app cache", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
