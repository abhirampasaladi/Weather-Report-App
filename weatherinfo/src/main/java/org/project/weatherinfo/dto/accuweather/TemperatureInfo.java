package org.project.weatherinfo.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureInfo {

    @JsonProperty("Imperial")
    private MetricsInfo temperatureInFahrenheit;

    @JsonProperty("Metric")
    private MetricsInfo temperatureInCelsius;
}

