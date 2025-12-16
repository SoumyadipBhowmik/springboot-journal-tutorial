package net.engineeringdigest.journalApp.controller;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journals")
@AllArgsConstructor
public class JournalController {

    private final JournalService journalService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntryOfUser(@RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        JournalEntry journalEntry = journalService.createJournalEntry(entry, userName);
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> allJournalEntries = user.getJournalEntries();
        if (allJournalEntries != null && !allJournalEntries.isEmpty()) {
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(id))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalService.getJournalEntryById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean entry = journalService.deleteJournalEntryById(id, userName);
        if (entry) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry journalEntry
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(entry -> entry.getId()
                .equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            JournalEntry entry = journalService
                    .createJournalEntry(journalService.updateJournalEntry(id, journalEntry));
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
