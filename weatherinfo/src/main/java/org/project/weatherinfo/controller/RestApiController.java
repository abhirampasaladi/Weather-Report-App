package org.project.weatherinfo.controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.WrongInputType;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.PostalCodeDTO;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.accuweather.AccuWeatherExtract;
import org.project.weatherinfo.service.visualcrossing.VCWeatherExtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @GetMapping("/city")
    public ResponseEntity<AccuAndVCWeatherInfo> getCity(@Valid @RequestBody PostalCodeDTO postalCodeObj,BindingResult bindingResult) throws ExecutionException, InterruptedException {
        String postalCode = postalCodeObj.getPostalCode();
            if (bindingResult.hasErrors()) {
                throw new WrongInputType(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
        log.info("In RestApiController {}", profileEnv);
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
        return ResponseEntity.ok(accuAndVCWeatherInfo);
    }
}
