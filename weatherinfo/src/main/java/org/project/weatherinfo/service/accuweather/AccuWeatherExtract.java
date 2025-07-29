package org.project.weatherinfo.service.accuweather;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.accuweather.CityKeyInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@Service
@Slf4j
@Setter
public class AccuWeatherExtract implements Callable<AccuWeatherInfo> {


    @Value("${acc_api_key}")
    private String accuApiKey;

    @Value("${acc_apikey_url}")
    private String accApiUrl;

    @Value("${acc_apikey_url_path}")
    private String accApiUrlPath;

    @Value("${acc_apikey_url_query_parameters}")
    private String accApiUrlQuery;

    @Value("${acc_url}")
    private String accUrl;

    @Value("${acc_url_path}")
    private String accUrlPath;

    @Value("${acc_url_query_parameters}")
    private String accUrlQueryParameters;

    private final RestTemplate restTemplate;

    private String postalCode;

    public AccuWeatherExtract(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public CityKeyInfo getCityKeyInfo(String postalCode) {
        String url = accApiUrl+accApiUrlPath+accApiUrlQuery.replace("{API_KEY}", accuApiKey).replace("{postalCode}", postalCode);
        ResponseEntity<CityKeyInfo[]> responseEntity = restTemplate.getForEntity(url, CityKeyInfo[].class);
        assert responseEntity.getBody() != null;
        CityKeyInfo cityKeyInfo = responseEntity.getBody()[0];
        log.debug("City Key: {}", cityKeyInfo);
        return cityKeyInfo;
    }

    @Override
    public AccuWeatherInfo call(){
        log.info("In AccuWeatherExtract call");
        CityKeyInfo cityKeyInfo = getCityKeyInfo(postalCode);
        String url= accUrl+ accUrlPath.replace("{locationKey}", cityKeyInfo.getCityKey())+ accUrlQueryParameters.replace("{API_KEY}", accuApiKey);
        ResponseEntity<AccuWeatherInfo[]> responseEntity = restTemplate.getForEntity(url, AccuWeatherInfo[].class);
        assert responseEntity.getBody() != null;
        AccuWeatherInfo weatherDetails = responseEntity.getBody()[0];
        log.info("In AccuWeatherExtract call");
        return weatherDetails;
    }
}
