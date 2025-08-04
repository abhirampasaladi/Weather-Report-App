package org.project.weatherinfo.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.service.DBCrudService;
import org.project.weatherinfo.service.WeatherInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class SchedulerHandler {
    private final DBCrudService dbCrudService;
    private final WeatherInfoService weatherInfoService;

    public SchedulerHandler(DBCrudService dbCrudService, WeatherInfoService weatherInfoService) {
        this.dbCrudService = dbCrudService;
        this.weatherInfoService = weatherInfoService;
    }

    @Async
    @Scheduled(cron="0 0 */1 * * *")
    public void setDbCrudService() throws JsonProcessingException, ExecutionException, InterruptedException {
        log.info("DBStoreSchedule Running......");
        String postalCode = "19425";
        dbCrudService.updateDB(weatherInfoService.retrieveCurrentWeatherInfo(postalCode), postalCode);
    }
}
