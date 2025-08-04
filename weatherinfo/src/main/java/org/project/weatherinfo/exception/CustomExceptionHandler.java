package org.project.weatherinfo.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.project.weatherinfo.payload.ApiResponseWrapper;
import org.project.weatherinfo.enums.HttpStatusCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleQueryAndPathValidationFailException(ConstraintViolationException exception, HttpServletRequest request) {
        String message = exception.getConstraintViolations().stream().map(v->v.getPropertyPath() + ": " + v.getMessage()).collect(Collectors.joining(","));
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.BAD_REQUEST.getCode(), HttpStatusCodes.BAD_REQUEST,
                message,
                request.getRequestURI()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleMissingParamException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        String message = exception.getParameterName() + " Parameter is missing!" ;
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.BAD_REQUEST.getCode(), HttpStatusCodes.BAD_REQUEST,
                message,
                request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleTypeException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        String message = exception.getName() + " has Invalid Type "+ exception.getValue() + " instead of "+exception.getRequiredType();
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.BAD_REQUEST.getCode(), HttpStatusCodes.BAD_REQUEST,
                message,
                request.getRequestURI()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleNoControllerFoundException(NoHandlerFoundException exception, HttpServletRequest request) {
        String message = "No handler found for " + exception.getHttpMethod() + " " + exception.getRequestURL();
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.NOT_FOUND.getCode(), HttpStatusCodes.NOT_FOUND,
                message,
                request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleHttpMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        String message = "Wrong Http RequestMethod, " + exception.getSupportedHttpMethods();
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.NOT_ALLOWED.getCode(), HttpStatusCodes.NOT_ALLOWED,
                message,
                request.getRequestURI()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleHttpClientErrorException(HttpClientErrorException exception, HttpServletRequest request) {
        String message = "Outbound API Request Failed, ";
        if(exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.UNAUTHORIZED.getCode(), HttpStatusCodes.UNAUTHORIZED,
                    message + exception.getStatusText()+" : Incorrect API-KEY or Missing QueryParams/PathVariables !",
                    request.getRequestURI()));
        }
        else if(exception.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.NOT_FOUND.getCode(), HttpStatusCodes.NOT_FOUND,
                    message + exception.getStatusText()+" : Incorrect URL !",
                    request.getRequestURI()));
        }
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.BAD_REQUEST.getCode(), HttpStatusCodes.BAD_REQUEST,
                message + exception.getStatusText(),
                request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWrapper<String>> handleException(Exception exception, HttpServletRequest request) {
        if (exception.getCause() instanceof HttpClientErrorException httpException) {
            return handleHttpClientErrorException(httpException, request);
        }
        String message = "Looks like Something is Wrong!, "+ exception.getMessage();
        return ResponseEntity.ok(new ApiResponseWrapper<>(LocalDateTime.now(), HttpStatusCodes.BAD_REQUEST.getCode(), HttpStatusCodes.BAD_REQUEST,
                message,
                request.getRequestURI()));
    }
}
