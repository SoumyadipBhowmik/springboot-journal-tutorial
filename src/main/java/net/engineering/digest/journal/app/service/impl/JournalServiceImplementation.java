package net.engineering.digest.journal.app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.entity.JournalEntry;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.repository.JournalRepository;
import net.engineering.digest.journal.app.service.JournalService;
import net.engineering.digest.journal.app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class JournalServiceImplementation implements JournalService {

    private final JournalRepository journalRepository;
    private final UserService userService;

    @Transactional
    public JournalEntry createJournalEntry(JournalEntry entry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            return saved;
        } catch (Exception e) {
            log.warn("An error occurred when trying to save journal for a user.",  e);
            throw new RuntimeException("An error occurred when trying to save journal for a user.", e);
        }
    }

    @Override
    public JournalEntry createJournalEntry(JournalEntry entry) {
        return journalRepository.save(entry);
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalRepository.findById(String.valueOf(id));
    }

    public List<JournalEntry> findJournalEntriesByUsername(String userName) {
        return null;
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalRepository.deleteById(String.valueOf(id));
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when trying to delete journal entry", e);
        }
        return removed;
    }

    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry) {
        JournalEntry journalEntry = journalRepository
                .findById(String.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Not found"));
        if (journalEntry != null) {
            journalEntry.setTitle(!entry.getTitle().isEmpty()
                    ? entry.getTitle()
                    : journalEntry.getTitle());
            journalEntry
                    .setContent(entry.getContent() != null && !entry.getContent().isEmpty()
                            ? entry.getContent()
                            : journalEntry.getContent());
        }
        return journalEntry;
    }

}
