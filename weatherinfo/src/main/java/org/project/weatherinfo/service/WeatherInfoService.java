package org.project.weatherinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.accuweather.AccuWeatherExtract;
import org.project.weatherinfo.service.visualcrossing.VCWeatherExtract;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class WeatherInfoService {

    private final AccuWeatherExtract accuWeatherExtract;
    private final VCWeatherExtract vcWeatherExtract;
    private final DBCrudService dbCrudService;

    public WeatherInfoService(AccuWeatherExtract accuWeatherExtract, VCWeatherExtract vcWeatherExtract, DBCrudService dbCrudService) {
        this.accuWeatherExtract = accuWeatherExtract;
        this.vcWeatherExtract = vcWeatherExtract;
        this.dbCrudService = dbCrudService;
    }

    public ResponseEntity<AccuAndVCWeatherInfo> retrieveWeatherInfo(String postalCode) throws ExecutionException, InterruptedException, JsonProcessingException {
        Future<AccuWeatherInfo> accuWeatherInfoFuture;
        Future<VCWeatherInfo> vcWeatherInfoFuture;
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            accuWeatherExtract.setPostalCode(postalCode);
            vcWeatherExtract.setPostalCode(postalCode);
            accuWeatherInfoFuture = executorService.submit(accuWeatherExtract);
            vcWeatherInfoFuture = executorService.submit(vcWeatherExtract);
            executorService.shutdown();
        }
        AccuAndVCWeatherInfo accuAndVCWeatherInfo = new AccuAndVCWeatherInfo(accuWeatherInfoFuture.get(), vcWeatherInfoFuture.get());
        dbCrudService.updateDB(accuAndVCWeatherInfo);
        return ResponseEntity.ok(accuAndVCWeatherInfo);
    }
}
