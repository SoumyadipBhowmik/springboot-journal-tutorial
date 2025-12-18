package net.engineering.digest.journal.app.service.impl;

import lombok.RequiredArgsConstructor;
import net.engineering.digest.journal.app.api.response.WeatherResponse;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.constants.Placeholders;
import net.engineering.digest.journal.app.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherServiceImplementation implements WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final AppCache appCache;

    public ResponseEntity<WeatherResponse> getWeather(String city) {
        String finalApi = appCache.getCache().get(AppCache.keys.WEATHER_API.toString())
                .replace(Placeholders.API_KEY, apiKey)
                .replace(Placeholders.CITY, city);
        return restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
    }
}
