package org.project.weatherinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.project.weatherinfo.enums.HttpStatusCodes;

@Builder
@Setter
@Getter
public class WeatherDataDTO {

    @JsonProperty("Postal-Code")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String postalCode = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("DateTime")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String dateTime = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Is-Day-Time?")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String isDayTime = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Temperature")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String temperature = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Feels-Like")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String feelsLike = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Humidity")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String humidity = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Dew")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String dew = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Has-Precipitation?")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String hasPrecipitation = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Precipitation Value")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String precipitation = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Snow")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String snow = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Snow-Depth")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String snowDepth = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Wind-Speed")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String windSpeed = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Wind-Direction")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String windDirection = HttpStatusCodes.NO_DATA_FOUND.getCode();

    @JsonProperty("Weather-Condition")
    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private String weatherConditions = HttpStatusCodes.NO_DATA_FOUND.getCode();
}
