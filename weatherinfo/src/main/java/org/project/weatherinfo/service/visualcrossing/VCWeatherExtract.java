package org.project.weatherinfo.service.visualcrossing;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.project.weatherinfo.dto.visualcrossing.VCWeatherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Callable;

@Service
@Slf4j
@Setter
public class VCWeatherExtract implements Callable<VCWeatherInfo> {

    @Value("${vc_api_key}")
    private String vcApiKey;

    @Value("${vc_url}")
    private String vcUrl;

    @Value("${vc_url_path}")
    private String vcUrlPath;

    @Value("${vc_url_query_parameters}")
    private String vcUrlQuery;

    private String postalCode;

    private final RestTemplate restTemplate;

    public VCWeatherExtract(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VCWeatherInfo call() throws Exception {
        log.info("In VC WeatherExtract call");
        String url = vcUrl + vcUrlPath.replace("{postalCode}", postalCode) + vcUrlQuery.replace("{cv_api_key}", vcApiKey);
        VCWeatherInfo vcWeatherInfo = restTemplate.getForObject(url, VCWeatherInfo.class);
//        WebClient.Builder clientBuilder = WebClient.builder();
//        VCWeatherInfo vcWeatherInfo = clientBuilder.build().get().uri(url).retrieve().bodyToMono(VCWeatherInfo.class).block();
        log.info("In VC WeatherExtract call");
        return vcWeatherInfo;
    }
}


