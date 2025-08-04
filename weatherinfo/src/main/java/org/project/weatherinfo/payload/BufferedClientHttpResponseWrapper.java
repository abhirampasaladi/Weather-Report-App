package org.project.weatherinfo.payload;

import org.springframework.lang.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedClientHttpResponseWrapper implements ClientHttpResponse{
    private final ClientHttpResponse response;
    private final byte[] buffer;

    public BufferedClientHttpResponseWrapper(ClientHttpResponse response) throws IOException {
        this.response = response;
        this.buffer = StreamUtils.copyToByteArray(response.getBody());
    }

    @Override
    public @NonNull HttpStatusCode getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public @NonNull String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public @NonNull InputStream getBody() {
        return new ByteArrayInputStream(buffer);
    }

    @Override
    public @NonNull HttpHeaders getHeaders() {
        return response.getHeaders();
    }
}
