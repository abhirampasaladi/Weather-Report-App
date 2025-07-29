package org.project.weatherinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Profile("dev")
@Slf4j
public class WeatherinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherinfoApplication.class, args);
        log.info("WeatherinfoApplication started in " +System.getenv("profile.status"));
    }

    @Bean
    public RestTemplate setRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        OutboundLogInterceptor interceptor = new OutboundLogInterceptor();
        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }
}
