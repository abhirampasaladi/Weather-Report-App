package org.project.weatherinfo.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.WrongInputType;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.project.weatherinfo.dto.PostalCodeDTO;
import org.project.weatherinfo.service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/weatherinfo")
@Slf4j
public class RestApiController {

    private final ObjectMapper objectMapper;
    private final WeatherInfoService weatherInfoService;

    @Value("${profile.status}")
    private String profileEnv;

    @Autowired
    RestApiController(ObjectMapper objectMapper, WeatherInfoService weatherInfoService) {

        this.objectMapper = objectMapper;
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping("/city")
    public ResponseEntity<AccuAndVCWeatherInfo> getCity(@Valid @RequestBody PostalCodeDTO postalCodeObj,BindingResult bindingResult) throws ExecutionException, InterruptedException, JsonProcessingException {
        String postalCode = postalCodeObj.getPostalCode();
            if (bindingResult.hasErrors()) {
                throw new WrongInputType(bindingResult.getAllErrors().getFirst().getDefaultMessage());
            }
        log.info("In RestApiController {}", profileEnv);
        return weatherInfoService.retrieveWeatherInfo(postalCode) ;
    }
}
