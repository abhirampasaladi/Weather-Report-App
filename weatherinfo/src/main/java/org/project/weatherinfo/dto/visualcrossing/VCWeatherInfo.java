package org.project.weatherinfo.dto.visualcrossing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VCWeatherInfo {

    @JsonProperty("resolvedAddress")
    private String city;

    @JsonProperty("currentConditions")
    private CurrentWeatherInfo currentWeatherInfo;
}