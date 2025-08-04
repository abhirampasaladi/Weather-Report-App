package org.project.weatherinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.project.weatherinfo.enums.HttpStatusCodes;

@Getter
@Setter
@AllArgsConstructor
public class AllWeatherInfoWrapper {

    @JsonProperty("Accu-Weather Information")
    private WeatherDataDTO accuWeatherInfo;

    @JsonProperty("Visual-Crossing Information")
    private WeatherDataDTO vcWeatherInfo;
}
