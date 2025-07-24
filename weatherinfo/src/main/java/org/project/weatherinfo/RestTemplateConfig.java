package org.project.weatherinfo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate setRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        OutboundLogInterceptor interceptor = new OutboundLogInterceptor();
        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }

}
