package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class JournalServiceImplementation implements JournalService {

    @Autowired
    private JournalRepository journalRepository;

    public JournalEntry createJournalEntry(JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        return journalRepository.save(entry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalRepository.findById(String.valueOf(id));
    }

    public void deleteJournalEntryById(ObjectId id) {
        journalRepository.deleteById(String.valueOf(id));
    }

    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry) {
        JournalEntry journalEntry = journalRepository
                .findById(String.valueOf(id)).orElseThrow(() -> new NoSuchElementException("Not found"));
        if (journalEntry != null) {
            journalEntry.setTitle(entry.getTitle() != null && !entry.equals("") ? entry.getTitle() : journalEntry.getTitle());
            journalEntry.setContent(entry.getContent() != null && !entry.equals("") ? entry.getContent() : journalEntry.getContent());
        }
        return journalEntry;
    }
}
