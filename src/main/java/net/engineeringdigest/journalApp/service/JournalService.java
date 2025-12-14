package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalService {
    public void createJournalEntry(JournalEntry entry, String userName);
    public List<JournalEntry> getAllJournalEntries();
    public Optional<JournalEntry> getJournalEntryById(ObjectId id);
    public void deleteJournalEntryById(ObjectId id);
    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry);
}
