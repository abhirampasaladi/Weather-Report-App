package org.project.weatherinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class OutboundLogInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("--------------------Outbound Request Log------------------------");
        log.info("Outbound Request at Interceptor: {}",request.getURI());
        log.info("Outbound Request Headers: {}",request.getHeaders());
        log.info("Outbound Request Method: {}",request.getMethod());
        log.info("--------------------Outbound Request Log-----------------------");
        ClientHttpResponse response = new BufferedClientHttpResponseWrapper(execution.execute(request, body));
        String responseBody = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        log.info("===================Outbound Request: Response Log================");
        log.info("Outbound Response Body: {}",responseBody);
        log.info("Outbound Request-Response at Interceptor: {}",response.getStatusCode());
        log.info("===================Outbound Request: Response Log===============");
        return response;
    }
}
