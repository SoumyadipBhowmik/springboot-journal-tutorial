package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalService {
    public JournalEntry createJournalEntry(JournalEntry entry);

    public JournalEntry createJournalEntry(JournalEntry entry, String userName);

    public Optional<JournalEntry> getJournalEntryById(ObjectId id);

    public List<JournalEntry> findJournalEntriesByUsername(String userName);

    public boolean deleteJournalEntryById(ObjectId id, String userName);

    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry);

}
