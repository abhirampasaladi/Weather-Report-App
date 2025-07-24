package org.project.weatherinfo.dto.visualcrossing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherInfo {

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("temp")
    private String temperature;

    @JsonProperty("feelslike")
    private String feelsLike;

    @JsonProperty("humidity")
    private String humidity;

    @JsonProperty("dew")
    private String dew;

    @JsonProperty("precip")
    private String precipitation;

    @JsonProperty("snow")
    private String snow;

    @JsonProperty("snowdepth")
    private String snowDepth;

    @JsonProperty("windspeed")
    private String windSpeed;

    @JsonProperty("winddir")
    private String windDirection;

    @JsonProperty("conditions")
    private String weatherConditions;
}