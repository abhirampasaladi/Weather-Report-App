package org.project.weatherinfo.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.WrongInputType;
import org.project.weatherinfo.dao.CrudRepo;
import org.project.weatherinfo.dao.PastDataDB;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.PostalCodeDTO;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.accuweather.AccuWeatherExtract;
import org.project.weatherinfo.service.visualcrossing.VCWeatherExtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    private final CrudRepo crudRepo;
    private final ObjectMapper objectMapper;

    @Value("${profile.status}")
    private String profileEnv;

    @Autowired
    RestApiController(AccuWeatherExtract accuWeatherExtract, VCWeatherExtract vcWeatherExtract, CrudRepo crudRepo, ObjectMapper objectMapper) {
        this.accuWeatherExtract = accuWeatherExtract;
        this.vcWeatherExtract = vcWeatherExtract;
        this.crudRepo = crudRepo;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/city")
    public ResponseEntity<AccuAndVCWeatherInfo> getCity(@Valid @RequestBody PostalCodeDTO postalCodeObj,BindingResult bindingResult) throws ExecutionException, InterruptedException, JsonProcessingException {
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
        LocalDateTime localDateTime = LocalDateTime.now();
        PastDataDB pastDataDB = new PastDataDB(localDateTime, objectMapper.writeValueAsString(accuAndVCWeatherInfo));
        crudRepo.save(pastDataDB);
        return ResponseEntity.ok(accuAndVCWeatherInfo);
    }
}
