package net.engineering.digest.journal.app.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.engineering.digest.journal.app.entity.ConfigJournalAppEntity;
import net.engineering.digest.journal.app.repository.ConfigJournalRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Getter
    private Map<String, String> cache;
    private ConfigJournalRepository configRepository;

    @PostConstruct
    public void init() {
        cache = new HashMap<>();
        List<ConfigJournalAppEntity> appEntities = configRepository.findAll();
        appEntities.forEach(entity -> cache.put(entity.getKey(), entity.getValue()));
    }
}
