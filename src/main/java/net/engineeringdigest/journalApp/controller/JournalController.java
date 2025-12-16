package net.engineeringdigest.journalApp.controller;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
@AllArgsConstructor
public class JournalController {

    private final JournalService journalService;
    private final UserService userService;

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createJournalEntryOfUser(@RequestBody JournalEntry entry, @PathVariable String userName) {
        journalService.createJournalEntry(entry, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allJournalEntries = user.getJournalEntries();
        if (allJournalEntries != null && !allJournalEntries.isEmpty()) {
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id ) {
        Optional<JournalEntry> journalEntryById = journalService.getJournalEntryById(id);
        return journalEntryById
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{userName}/{id}")

    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id, @PathVariable String userName) {
        journalService.deleteJournalEntryById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId id,
            @PathVariable String userName,
            @RequestBody JournalEntry journalEntry
    ) {
        try {
            JournalEntry entry = journalService.createJournalEntry(journalService.updateJournalEntry(id, journalEntry));
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
