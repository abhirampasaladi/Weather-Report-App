package org.project.weatherinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class PastDataDTO {

    @JsonProperty("Local-DateTime")
    private final LocalDateTime localDateTime;

    @JsonProperty("Multiple WeatherSources Information")
    private final AllWeatherInfoWrapper allWeatherInfoWrapper;
}
