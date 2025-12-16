package net.engineeringdigest.journalApp.service.impl;

import lombok.AllArgsConstructor;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JournalServiceImplementation implements JournalService {

    private final JournalRepository journalRepository;
    private final UserService userService;

    @Transactional
    public void createJournalEntry(JournalEntry entry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.createUser(user);
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred when trying to save journal for a user.", e);
        }
    }

    @Override
    public JournalEntry createJournalEntry(JournalEntry entry) {
        return journalRepository.save(entry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalRepository.findById(String.valueOf(id));
    }

    public void deleteJournalEntryById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userService.createUser(user);
        journalRepository.deleteById(String.valueOf(id));
    }

    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry) {
        JournalEntry journalEntry = journalRepository
                .findById(String.valueOf(id)).orElseThrow(() -> new NoSuchElementException("Not found"));
        if (journalEntry != null) {
            journalEntry.setTitle(!entry.equals("") ? entry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(entry.getContent() != null && !entry.equals("") ? entry.getContent() : journalEntry.getContent());
        }
        return journalEntry;
    }
}
