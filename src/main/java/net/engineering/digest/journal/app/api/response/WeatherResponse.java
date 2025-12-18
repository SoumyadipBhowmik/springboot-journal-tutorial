package net.engineering.digest.journal.app.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current {

        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        @JsonProperty("feels_like")
        private int feelslike;
        @JsonProperty("is_day")
        private String isDay;
    }
}