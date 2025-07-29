package org.project.weatherinfo.controller;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.accuweather.AccuWeatherExtract;
import org.project.weatherinfo.service.visualcrossing.VCWeatherExtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/weatherinfo")
@Slf4j
public class RestApiController {

    private final AccuWeatherExtract accuWeatherExtract;

    private final VCWeatherExtract vcWeatherExtract;

    @Value("${profile.status}")
    private String profileEnv;

    @Autowired
    RestApiController(AccuWeatherExtract accuWeatherExtract, VCWeatherExtract vcWeatherExtract) {
        this.accuWeatherExtract = accuWeatherExtract;
        this.vcWeatherExtract = vcWeatherExtract;
    }

    @GetMapping("/{postalCode}")
    public ResponseEntity<AccuAndVCWeatherInfo> getCity(@PathVariable String postalCode) throws ExecutionException, InterruptedException {
        log.info("In RestApiController {}", profileEnv);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        accuWeatherExtract.setPostalCode(postalCode);
        vcWeatherExtract.setPostalCode(postalCode);
        Future<AccuWeatherInfo> accuWeatherInfoFuture = executorService.submit(accuWeatherExtract);
        Future<VCWeatherInfo> vcWeatherInfoFuture = executorService.submit(vcWeatherExtract);
        AccuAndVCWeatherInfo accuAndVCWeatherInfo = new AccuAndVCWeatherInfo(accuWeatherInfoFuture.get(), vcWeatherInfoFuture.get());
        return ResponseEntity.ok(accuAndVCWeatherInfo);
    }
}
