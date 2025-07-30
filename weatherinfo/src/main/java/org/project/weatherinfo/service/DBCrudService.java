package org.project.weatherinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.weatherinfo.dao.CrudRepo;
import org.project.weatherinfo.dao.PastDataDB;
import org.project.weatherinfo.dto.AccuAndVCWeatherInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DBCrudService {

    private final CrudRepo crudRepo;
    private final ObjectMapper objectMapper;

    public DBCrudService(CrudRepo crudRepo, ObjectMapper objectMapper) {
        this.crudRepo = crudRepo;
        this.objectMapper = objectMapper;
    }

    public void updateDB(AccuAndVCWeatherInfo accuAndVCWeatherInfo) throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.now();
        PastDataDB pastDataDB = new PastDataDB(localDateTime, objectMapper.writeValueAsString(accuAndVCWeatherInfo));
        crudRepo.save(pastDataDB);
    }
}
