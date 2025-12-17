package net.engineering.digest.journal.app.repository;


import net.engineering.digest.journal.app.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalEntry, String> {
}
