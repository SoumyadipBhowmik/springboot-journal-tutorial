package net.engineering.digest.journal.app.service;

import net.engineering.digest.journal.app.api.response.WeatherResponse;
import org.springframework.http.ResponseEntity;

public interface WeatherService {

    ResponseEntity<WeatherResponse> getWeather(String city);
}
