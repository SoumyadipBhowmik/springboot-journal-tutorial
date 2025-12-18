package net.engineering.digest.journal.app.scheduler;

import lombok.AllArgsConstructor;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.entity.JournalEntry;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.repository.UserRepositoryImplementation;
import net.engineering.digest.journal.app.service.EmailService;
import net.engineering.digest.journal.app.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserScheduler {

    private final EmailService emailService;
    private final UserRepositoryImplementation userRepository;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
        List<User> users = userRepository.getUserForSentimentAnalysis();
        users.forEach(user -> {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(entry -> entry.getDate()
                    .isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent)
                    .collect(Collectors.toList());
            String entry = String.join("" + filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        });
    }

    @Scheduled(cron = "0 0/10 0 ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
