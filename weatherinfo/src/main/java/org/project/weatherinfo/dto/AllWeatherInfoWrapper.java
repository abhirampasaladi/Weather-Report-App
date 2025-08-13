package org.project.weatherinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllWeatherInfoWrapper {

    @JsonProperty("Accu-Weather Information")
    private WeatherDataDTO accuWeatherInfo;

    @JsonProperty("Visual-Crossing Information")
    private WeatherDataDTO vcWeatherInfo;
}
