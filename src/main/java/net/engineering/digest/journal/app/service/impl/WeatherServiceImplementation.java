package net.engineering.digest.journal.app.service.impl;

import lombok.AllArgsConstructor;
import net.engineering.digest.journal.app.api.response.WeatherResponse;
import net.engineering.digest.journal.app.service.WeatherService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class WeatherServiceImplementation implements WeatherService {

    private static final String API_KEY = "4bc381f8d78a220977883bde48bf8798";
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    private RestTemplate restTemplate;

    public ResponseEntity<WeatherResponse> getWeather(String city) {
        String finalApi = API.replace("API_KEY", API_KEY).replace("CITY", city);
        return restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
    }
}
