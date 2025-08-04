package org.project.weatherinfo.exception;

public class WeatherDataProcessingException extends RuntimeException {
    public WeatherDataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}