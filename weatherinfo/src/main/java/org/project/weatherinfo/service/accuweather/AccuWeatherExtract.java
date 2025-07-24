package org.project.weatherinfo.service.accuweather;

import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.accuweather.AccuWeatherInfo;
import org.project.weatherinfo.dto.accuweather.CityKeyInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@Slf4j
public class AccuWeatherExtract implements Callable<AccuWeatherInfo> {

    private final String accuApiKey;
    private final String postalCode;
    private final RestTemplate restTemplate;

    public AccuWeatherExtract(String accuApiKey, String postalCode, RestTemplate restTemplate) {
        this.accuApiKey = accuApiKey;
        this.postalCode = postalCode;
        this.restTemplate = restTemplate;
    }

    public CityKeyInfo getCityKey(String postalCode) {
        String url = "http://dataservice.accuweather.com/locations/v1/postalcodes/search?apikey={API_KEY}&q={postalCode}";
        url = url.replace("{API_KEY}", accuApiKey);
        url = url.replace("{postalCode}", postalCode);
        ResponseEntity<CityKeyInfo[]> responseEntity = restTemplate.getForEntity(url, CityKeyInfo[].class);
        assert responseEntity.getBody() != null;
        CityKeyInfo cityKeyInfo = responseEntity.getBody()[0];
        log.debug("City Key: {}", cityKeyInfo);
        return cityKeyInfo;
    }

    @Override
    public AccuWeatherInfo call(){
        log.info("In AccuWeatherExtract call");
        CityKeyInfo cityKeyInfo = getCityKey(postalCode);
        String url= "http://dataservice.accuweather.com/currentconditions/v1/{locationKey}?apikey={API_KEY}";
        url = url.replace("{API_KEY}", accuApiKey);
        url = url.replace("{locationKey}", cityKeyInfo.getCityKey());
        ResponseEntity<AccuWeatherInfo[]> responseEntity = restTemplate.getForEntity(url, AccuWeatherInfo[].class);
        AccuWeatherInfo weatherDetails = responseEntity.getBody()[0];
        log.info("In AccuWeatherExtract call");
        return weatherDetails;
    }
}
