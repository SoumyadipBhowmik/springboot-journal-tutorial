package net.engineering.digest.journal.app.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    void findByUsernameTest() {
        assertNotNull(userService.findByUserName("soumyadip"));
    }

}
