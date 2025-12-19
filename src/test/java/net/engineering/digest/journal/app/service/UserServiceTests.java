package net.engineering.digest.journal.app.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Disabled
    @Test
    void findByUsernameTest() {
        assertNotNull(userService.findByUserName("soumyadip"));
    }

    @Test
    void sendTestRedisTemplate() {
//        redisTemplate.opsForValue().set("email", "bhowmik.soumyadip9.gmail.com");
        String email = redisTemplate.opsForValue().get("name");
        int a = 1;

    }

}
