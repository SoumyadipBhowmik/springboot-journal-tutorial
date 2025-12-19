package net.engineering.digest.journal.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journal.app.api.response.WeatherResponse;
import net.engineering.digest.journal.app.cache.AppCache;
import net.engineering.digest.journal.app.constants.Placeholders;
import net.engineering.digest.journal.app.service.RedisService;
import net.engineering.digest.journal.app.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImplementation implements WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final AppCache appCache;
    private final RedisService redisService;

    public ResponseEntity<WeatherResponse> getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
        } else {
            String finalApi = appCache.getCache().get(AppCache.keys.WEATHER_API.toString())
                    .replace(Placeholders.API_KEY, apiKey)
                    .replace(Placeholders.CITY, city);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse responseBody = response.getBody();
            if (responseBody != null) {
                redisService.set("weather_of_" + city, responseBody, 300L);
                log.info("Storing in redis: {}", responseBody.getCurrent().toString());
            }
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }
}
