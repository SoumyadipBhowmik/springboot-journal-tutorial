package net.engineering.digest.journal.app.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AppCache appCache;
    private final PagedResourcesAssembler<User> assembler;

    @GetMapping("/all-users")
    public ResponseEntity<PagedModel<EntityModel<User>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> allUsers = userService.getAllUsers(pageable);
        return ResponseEntity.ok(assembler.toModel(allUsers));
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<User> createAdminUser(@RequestBody User user) {
            userService.createAdminUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
