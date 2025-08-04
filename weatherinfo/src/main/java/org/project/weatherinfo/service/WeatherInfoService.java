package org.project.weatherinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.weatherinfo.dto.AllWeatherInfoWrapper;
import org.project.weatherinfo.dto.PastDataDTO;
import org.project.weatherinfo.dto.PastDataTempDTO;
import org.project.weatherinfo.dto.WeatherDataDTO;
import org.project.weatherinfo.exception.WeatherDataProcessingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class WeatherInfoService {

    private final DBCrudService dbCrudService;
    private final ObjectMapper objectMapper;
    private final Map<String, WeatherExtractService> weatherExtractServiceMap;

    public WeatherInfoService(DBCrudService dbCrudService, ObjectMapper objectMapper, Map<String, WeatherExtractService> weatherExtractServiceMap) {
        this.dbCrudService = dbCrudService;
        this.objectMapper = objectMapper;
        this.weatherExtractServiceMap = weatherExtractServiceMap;
    }

    public AllWeatherInfoWrapper retrieveCurrentWeatherInfo(String postalCode) throws ExecutionException, InterruptedException {
        CompletableFuture<WeatherDataDTO> acFuture = weatherExtractServiceMap.get("acWeatherInfo").weatherExtract(postalCode);
        CompletableFuture<WeatherDataDTO> vcFuture = weatherExtractServiceMap.get("vcWeatherInfo").weatherExtract(postalCode);
        CompletableFuture.allOf(acFuture, vcFuture).join();
        return new AllWeatherInfoWrapper(acFuture.get(), vcFuture.get());
    }

    public PastDataTempDTO retrieveCurrentWeatherTemperatures(String postalCode) throws ExecutionException, InterruptedException {
        CompletableFuture<WeatherDataDTO> acFuture = weatherExtractServiceMap.get("acWeatherInfo").weatherExtract(postalCode);
        CompletableFuture<WeatherDataDTO> vcFuture = weatherExtractServiceMap.get("vcWeatherInfo").weatherExtract(postalCode) ;
        CompletableFuture.allOf(acFuture, vcFuture).join();
        AllWeatherInfoWrapper accuAndVCWeatherInfo = new AllWeatherInfoWrapper(acFuture.get(), vcFuture.get());
        return PastDataTempDTO.builder()
                .dateTime(LocalDateTime.now())
                .acTemperature(accuAndVCWeatherInfo.getAccuWeatherInfo().getTemperature() +" F")
                .vcTemperature(accuAndVCWeatherInfo.getVcWeatherInfo().getTemperature()+ " C")
                .postalCode(postalCode)
                .acWeatherCondition(accuAndVCWeatherInfo.getAccuWeatherInfo().getWeatherConditions())
                .vcWeatherCondition(accuAndVCWeatherInfo.getVcWeatherInfo().getWeatherConditions())
                .build();
    }

    public List<PastDataDTO> retrieveWeatherInPastHrs(String postalCode, LocalDateTime fromDate, LocalDateTime toDate) {
        List<PastDataDTO> listPastData = new ArrayList<>();
        dbCrudService.getAllDB().forEach(pastDataDB -> {
            if(pastDataDB.getLocalDateTime().isBefore(toDate) && pastDataDB.getLocalDateTime().isAfter(fromDate) && pastDataDB.getPostalCode().equals(postalCode)) {
                try {
                    listPastData.add(new PastDataDTO(pastDataDB.getLocalDateTime(), objectMapper.readValue(pastDataDB.getAccuAndVCWeatherInfo(), AllWeatherInfoWrapper.class)));
                } catch (JsonProcessingException e) {
                    throw new WeatherDataProcessingException("Failed to parse weather info JSON for postalCode: " + postalCode, e);
                }
            }
        });
        return listPastData;
    }

    public List<PastDataTempDTO> retrieveTemperaturesInPast(String postalCode, LocalDateTime fromDate, LocalDateTime toDate) {
        List<PastDataTempDTO> listPastDataTemp = new ArrayList<>();
        dbCrudService.getAllDB().forEach(pastDataDB -> {
            if(pastDataDB.getLocalDateTime().isBefore(toDate) && pastDataDB.getLocalDateTime().isAfter(fromDate) && pastDataDB.getPostalCode().equals(postalCode)) {
                listPastDataTemp.add(new PastDataTempDTO(pastDataDB.getLocalDateTime(),postalCode,pastDataDB.getAccuWeatherInfoTemp(), pastDataDB.getVcWeatherInfoTemp(), pastDataDB.getVcWeatherInfoCondition(), pastDataDB.getAccuWeatherInfoCondition()));
            }
        });
        return listPastDataTemp;
    }
}
