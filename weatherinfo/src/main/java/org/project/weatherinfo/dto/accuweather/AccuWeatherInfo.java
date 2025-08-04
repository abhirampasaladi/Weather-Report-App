package org.project.weatherinfo.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccuWeatherInfo {
    @JsonProperty("LocalObservationDateTime")
    private String dateTime;

    @JsonProperty("WeatherText")
    private String weatherState;

    @JsonProperty("HasPrecipitation")
    private String hasPrecipitation;

    @JsonProperty("IsDayTime")
    private String isDayTime;

    @JsonProperty("Temperature")
    private TemperatureInfo temperatureInfo;
}