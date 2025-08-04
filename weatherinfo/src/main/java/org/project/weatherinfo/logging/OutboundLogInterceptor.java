package org.project.weatherinfo.logging;

import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.project.weatherinfo.payload.BufferedClientHttpResponseWrapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class OutboundLogInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @NonNull ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);
        log.info("Outbound Request -CorrelationId: {} URI: {}, Method: {}, Headers: {}, Body: {}",
                correlationId,
                request.getURI(),
                request.getMethod(),
                request.getHeaders(),
                new String(body, StandardCharsets.UTF_8));
        ClientHttpResponse response = new BufferedClientHttpResponseWrapper(execution.execute(request, body));
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        log.info("Outbound Response - CorrelationId: {} Status: {}, Headers: {}, Body: {}",
                    correlationId,
                    response.getStatusCode(),
                    response.getHeaders(),
                    responseBody);
        MDC.clear();
        return response;
    }
}
