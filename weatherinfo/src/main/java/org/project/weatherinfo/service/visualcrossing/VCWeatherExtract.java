package org.project.weatherinfo.service.visualcrossing;

import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Callable;

@Slf4j
public class VCWeatherExtract implements Callable<VCWeatherInfo> {

    private final String vcApiKey;

    private final String postalCode;

    public VCWeatherExtract(String postalCode,String vcApiKey) {
        this.postalCode = postalCode;
        this.vcApiKey = vcApiKey;
    }

    @Override
    public VCWeatherInfo call() throws Exception {
        log.info("In VC WeatherExtract call");
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{postalCode}/today?unitGroup=metric&include=current&key={cv_api_key}&contentType=json";
        url = url.replace("{postalCode}", postalCode);
        url = url.replace("{cv_api_key}", vcApiKey);
        WebClient.Builder clientBuilder = WebClient.builder();
        VCWeatherInfo vcWeatherInfo = clientBuilder.build().get().uri(url).retrieve().bodyToMono(VCWeatherInfo.class).block();
        log.info("In VC WeatherExtract call");
        return vcWeatherInfo;
    }
}


