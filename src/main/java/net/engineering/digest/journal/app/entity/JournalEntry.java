package net.engineering.digest.journal.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.engineering.digest.journal.app.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "journal_entries")
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    @NonNull
    private Sentiment sentiment;
}
