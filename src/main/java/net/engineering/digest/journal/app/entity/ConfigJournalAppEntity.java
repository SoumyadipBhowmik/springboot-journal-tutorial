package net.engineering.digest.journal.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("config_journal_app")
@NoArgsConstructor
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
