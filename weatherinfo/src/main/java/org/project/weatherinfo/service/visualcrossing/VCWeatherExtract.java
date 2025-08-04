package org.project.weatherinfo.service.visualcrossing;

import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.WeatherDataDTO;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.project.weatherinfo.service.WeatherExtractService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service("vcWeatherInfo")
@Slf4j
public class VCWeatherExtract implements WeatherExtractService {

    @Value("${vc_api_key}")
    private String vcApiKey;

    @Value("${vc_url}")
    private String vcUrl;

    @Value("${vc_url_path}")
    private String vcUrlPath;

    @Value("${vc_url_query_parameters}")
    private String vcUrlQuery;

    private final RestTemplate restTemplate;

    public VCWeatherExtract(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Async
    public CompletableFuture<WeatherDataDTO> weatherExtract(String postalCode) {
        String url = vcUrl + vcUrlPath.replace("{postalCode}", postalCode) + vcUrlQuery.replace("{cv_api_key}", vcApiKey);
        VCWeatherInfo vcWeatherInfo = restTemplate.getForObject(url, VCWeatherInfo.class);
        assert vcWeatherInfo != null;
        return CompletableFuture.completedFuture(WeatherDataDTO.builder()
                .postalCode(postalCode)
                .dew(vcWeatherInfo.getCurrentWeatherInfo().getDew())
                .weatherConditions(vcWeatherInfo.getCurrentWeatherInfo().getWeatherConditions())
                .snow(vcWeatherInfo.getCurrentWeatherInfo().getSnow())
                .dateTime(vcWeatherInfo.getCurrentWeatherInfo().getDatetime())
                .temperature(vcWeatherInfo.getCurrentWeatherInfo().getTemperature())
                .feelsLike(vcWeatherInfo.getCurrentWeatherInfo().getFeelsLike())
                .humidity(vcWeatherInfo.getCurrentWeatherInfo().getHumidity())
                .precipitation(vcWeatherInfo.getCurrentWeatherInfo().getPrecipitation())
                .snow(vcWeatherInfo.getCurrentWeatherInfo().getSnow())
                .snowDepth(vcWeatherInfo.getCurrentWeatherInfo().getSnowDepth())
                .windSpeed(vcWeatherInfo.getCurrentWeatherInfo().getWindSpeed())
                .windDirection(vcWeatherInfo.getCurrentWeatherInfo().getWindDirection())
                .build());
    }
}


