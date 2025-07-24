package org.project.weatherinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;

@Getter
@Setter
@AllArgsConstructor
public class AccuAndVCWeatherInfo {
    private AccuWeatherInfo accuWeatherInfo;
    private VCWeatherInfo vcWeatherInfo;
}
