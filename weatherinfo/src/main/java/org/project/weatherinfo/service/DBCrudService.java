package org.project.weatherinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.weatherinfo.dao.CrudRepo;
import org.project.weatherinfo.dto.AllWeatherInfoWrapper;
import org.project.weatherinfo.model.PastDataDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DBCrudService {

    private final CrudRepo crudRepo;
    private final ObjectMapper objectMapper;

    public DBCrudService(CrudRepo crudRepo, ObjectMapper objectMapper) {
        this.crudRepo = crudRepo;
        this.objectMapper = objectMapper;
    }

    public void updateDB(AllWeatherInfoWrapper allWeatherInfo, String postalCode) throws JsonProcessingException {
        PastDataDB pastDataDB = PastDataDB
                .builder()
                .localDateTime(LocalDateTime.now())
                .postalCode(postalCode)
                .accuAndVCWeatherInfo(objectMapper.writeValueAsString(allWeatherInfo))
                .accuWeatherInfoDateTime(allWeatherInfo.getAccuWeatherInfo().getDateTime())
                .accuWeatherInfoCondition(allWeatherInfo.getAccuWeatherInfo().getWeatherConditions())
                .accuWeatherInfoTemp(allWeatherInfo.getAccuWeatherInfo().getTemperature() +" F")
                .vcWeatherInfoDateTime(allWeatherInfo.getVcWeatherInfo().getDateTime())
                .vcWeatherInfoTemp(allWeatherInfo.getVcWeatherInfo().getTemperature()+" C")
                .vcWeatherInfoFeelsLike(allWeatherInfo.getVcWeatherInfo().getFeelsLike() + " C")
                .vcWeatherInfoCondition(allWeatherInfo.getVcWeatherInfo().getWeatherConditions())
                .build();
        crudRepo.save(pastDataDB);
    }

    public List<PastDataDB> getAllDB(){
        return crudRepo.findAll();
    }
}
