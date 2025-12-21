package net.engineering.digest.journal.app.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.api.response.WeatherResponse;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.service.UserService;
import net.engineering.digest.journal.app.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final WeatherService weatherService;

    @PutMapping
    public ResponseEntity<User> updateUserByUserName(@RequestBody User updateUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User updatedUser = userService.updateUserByUserName(userName, updateUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserById() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            userService.deleteByUsername(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<WeatherResponse> weather = weatherService.getWeather("Bangalore");
        String greeting = "";
        if (weather != null && weather.getBody() != null) {
            try {
                greeting = "Today's temperature is: " + weather.getBody().getCurrent().getTemperature();
            } catch (Exception e) {
                log.warn("Error fetching weather", e);
            }
        }
        return new ResponseEntity<>("Hello, " + userName + ". " + greeting, HttpStatus.OK);
    }

}
