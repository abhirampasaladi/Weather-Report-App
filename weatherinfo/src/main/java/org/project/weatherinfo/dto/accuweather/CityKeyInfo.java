package org.project.weatherinfo.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityKeyInfo {

    @JsonProperty("Key")
    private String cityKey;

    @JsonProperty("PrimaryPostalCode")
    private String postalCode;

    @JsonProperty("LocalizedName")
    private String city;
}
