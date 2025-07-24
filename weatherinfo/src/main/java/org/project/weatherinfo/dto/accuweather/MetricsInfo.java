package org.project.weatherinfo.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricsInfo {

    @JsonProperty("Value")
    private String temperature;

    @JsonProperty("Unit")
    private String unitType;
}
