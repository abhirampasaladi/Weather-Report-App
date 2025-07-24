package org.project.weatherinfo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedClientHttpResponseWrapper implements ClientHttpResponse {
    private final ClientHttpResponse response;
    private byte[] buffer;

    public BufferedClientHttpResponseWrapper(ClientHttpResponse response) throws IOException {
        this.response = response;
        this.buffer = StreamUtils.copyToByteArray(response.getBody());
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(buffer);
    }

    @Override
    public HttpHeaders getHeaders() {
        return response.getHeaders();
    }
}
