package org.project.weatherinfo.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.project.weatherinfo.enums.HttpStatusCodes;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ApiResponseWrapper<T> {

    @JsonProperty("TimeStamp")
    private LocalDateTime timestamp;

    @JsonProperty("Request Status")
    private String status;

    @JsonProperty("Message")
    private HttpStatusCodes message;

    @JsonProperty("Data Received")
    private T data;

    @JsonProperty("URI used")
    private String path;

    public ApiResponseWrapper(LocalDateTime timestamp, String status, HttpStatusCodes message, T data, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
    }
}
