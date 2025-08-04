package org.project.weatherinfo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.AllWeatherInfoWrapper;
import org.project.weatherinfo.payload.ApiResponseWrapper;
import org.project.weatherinfo.enums.HttpStatusCodes;
import org.project.weatherinfo.dto.PastDataDTO;
import org.project.weatherinfo.dto.PastDataTempDTO;
import org.project.weatherinfo.service.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("weather/us/cities")
@Slf4j
@Validated
public class RestApiController {

    private final WeatherInfoService weatherInfoService;

    @Value("${profile.status}")
    private String profileEnv;

    @Autowired
    RestApiController(WeatherInfoService weatherInfoService) {
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping("/current-conditions")
    public ResponseEntity<ApiResponseWrapper<AllWeatherInfoWrapper>> getCityWeather(
            @NotBlank(message = "Postal Code Cannot be Null!")
            @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
            @RequestParam("postalcode") String postalCode,
            HttpServletRequest request) throws ExecutionException, InterruptedException {
        log.info("In RestApiController {}", profileEnv);
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.SUCCESS.getCode(), HttpStatusCodes.SUCCESS,
                weatherInfoService.retrieveCurrentWeatherInfo(postalCode),
                request.getRequestURI()));
    }

    @GetMapping("/current-conditions/temperatures")
    public ResponseEntity<ApiResponseWrapper<PastDataTempDTO>> getCityTemperature(
            @NotBlank(message = "Postal Code Cannot be Null!")
            @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
            @RequestParam("postalcode") String postalCode,
            HttpServletRequest request
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(new ApiResponseWrapper<>
                (LocalDateTime.now(),HttpStatusCodes.SUCCESS.getCode() , HttpStatusCodes.SUCCESS,
                weatherInfoService.retrieveCurrentWeatherTemperatures(postalCode),
                request.getRequestURI())
        );
    }

    @GetMapping("/history-reports")
    public ResponseEntity<ApiResponseWrapper<List<PastDataDTO>>> getDataInPastHours(
            @NotBlank(message = "Postal Code Cannot be Null!")
            @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
            @RequestParam("postalcode") String postalCode,
            @RequestParam("range")
            @NotNull(message = "Range cannot be Null!")
            int lastPastHour,
            HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                LocalDateTime.now(), HttpStatusCodes.SUCCESS.getCode(), HttpStatusCodes.SUCCESS,
                weatherInfoService.retrieveWeatherInPastHrs(postalCode,LocalDateTime.now().minusHours(lastPastHour), LocalDateTime.now()),
                request.getRequestURI()));
    }

    @GetMapping("/history-reports/temperature/in-past-hr")
    public ResponseEntity<ApiResponseWrapper<List<PastDataTempDTO>>> getTemperatureInPastHours(
            @NotBlank(message = "Postal Code Cannot be Null!")
            @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
            @RequestParam("postalcode") String postalCode,
            @RequestParam("range")
            @NotNull(message = "Range cannot be Null!")
            int lastPastHour,
            HttpServletRequest request){

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                LocalDateTime.now(), HttpStatusCodes.SUCCESS.getCode(), HttpStatusCodes.SUCCESS,
                weatherInfoService.retrieveTemperaturesInPast(postalCode, LocalDateTime.now().minusHours(lastPastHour), LocalDateTime.now()),
                request.getRequestURI()));
    }

    @GetMapping("/history-reports/temperature/between")
    public ResponseEntity<ApiResponseWrapper<List<PastDataTempDTO>>> getTemperatureInRange(
            @NotBlank(message = "Postal Code Cannot be Null!")
            @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be in the 5-4 / 5 digits in format 12345 or 12345-6789!")
            @RequestParam("postalcode") String postalCode,
            @NotBlank(message = "from DateTime Cannot be Null!")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{2}:\\d{2}$\n", message = "from Datetime Format is Incorrect YYYY-MM-DDTHH:MM:SS-04:00")
            @RequestParam("from") LocalDateTime fromDate,
            @NotBlank(message = "to DateTime Cannot be Null!")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{2}:\\d{2}$\n", message = "to Datetime Format is Incorrect YYYY-MM-DDTHH:MM:SS-04:00")
            @RequestParam("to") LocalDateTime toDate,
            HttpServletRequest request){
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                LocalDateTime.now(), HttpStatusCodes.SUCCESS.getCode(), HttpStatusCodes.SUCCESS,
                weatherInfoService.retrieveTemperaturesInPast(postalCode, fromDate, toDate),
                request.getRequestURI()));
    }
}
