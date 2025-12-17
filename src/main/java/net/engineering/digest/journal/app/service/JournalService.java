package net.engineering.digest.journal.app.service;

import net.engineering.digest.journal.app.entity.JournalEntry;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalService {
    JournalEntry createJournalEntry(JournalEntry entry);

    JournalEntry createJournalEntry(JournalEntry entry, String userName);

    Optional<JournalEntry> getJournalEntryById(ObjectId id);

    List<JournalEntry> findJournalEntriesByUsername(String userName);

    boolean deleteJournalEntryById(ObjectId id, String userName);

    JournalEntry updateJournalEntry(ObjectId id, JournalEntry entry);

}
