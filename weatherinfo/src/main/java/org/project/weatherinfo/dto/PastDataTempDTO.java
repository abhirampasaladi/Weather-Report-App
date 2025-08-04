package org.project.weatherinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PastDataTempDTO {
    @JsonProperty("DateTime")
    private final LocalDateTime dateTime;

    @JsonProperty("Postal-Code")
    private final String postalCode;

    @JsonProperty("Accu-Weather Temperature")
    private final String acTemperature;

    @JsonProperty("Visual-Crossing Temperature")
    private final String vcTemperature;

    @JsonProperty("Visual-Crossing State")
    private final String vcWeatherCondition;

    @JsonProperty("Accu-Weather State")
    private final String acWeatherCondition;
}
