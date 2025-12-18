package net.engineering.digest.journal.app.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.entity.JournalEntry;
import net.engineering.digest.journal.app.entity.User;
import net.engineering.digest.journal.app.enums.Sentiment;
import net.engineering.digest.journal.app.repository.UserRepositoryImplementation;
import net.engineering.digest.journal.app.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class UserScheduler {

    private final EmailService emailService;
    private final UserRepositoryImplementation userRepository;
    private final AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
        List<User> users = userRepository.getUserForSentimentAnalysis();
        users.forEach(user -> {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(entry -> entry.getDate()
                    .isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getSentiment)
                    .collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            sentiments.forEach(sentiment -> {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                }
            });
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        });
    }

    @Scheduled(cron = "0 0/10 0 ? * *")
    public void clearAppCache() {
        appCache.init();
        log.info("App cache cleared successfully");
    }
}
