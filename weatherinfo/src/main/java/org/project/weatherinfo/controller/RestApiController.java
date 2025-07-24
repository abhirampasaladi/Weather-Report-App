package org.project.weatherinfo.controller;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.accuweather.AccuWeatherExtract;
import org.project.weatherinfo.service.visualcrossing.VCWeatherExtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/weatherinfo")
public class RestApiController {

    private final RestTemplate restTemplate;

    @Value("${vc_api_key}")
    private String vcApiKey;

    @Value("${acc_api_key}")
    private String accuApiKey;

    @Autowired
    RestApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{postalCode}")
    public ResponseEntity<AccuAndVCWeatherInfo> getCity(@PathVariable String postalCode) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<AccuWeatherInfo> accuWeatherInfoFuture = executorService.submit(new AccuWeatherExtract(accuApiKey, postalCode, restTemplate));
        Future<VCWeatherInfo> vcWeatherInfoFuture = executorService.submit(new VCWeatherExtract(postalCode,vcApiKey));
        AccuAndVCWeatherInfo accuAndVCWeatherInfo = new AccuAndVCWeatherInfo(accuWeatherInfoFuture.get(), vcWeatherInfoFuture.get());
        return ResponseEntity.ok(accuAndVCWeatherInfo);
    }
}
