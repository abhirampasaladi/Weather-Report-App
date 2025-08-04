package org.project.weatherinfo.service;

import org.project.weatherinfo.dto.WeatherDataDTO;

import java.util.concurrent.CompletableFuture;

public interface WeatherExtractService {
    CompletableFuture<WeatherDataDTO> weatherExtract(String postalCode);
}
