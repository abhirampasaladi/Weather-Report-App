package org.project.weatherinfo.enums;


import lombok.Getter;

@Getter
public enum HttpStatusCodes {
    SUCCESS("200"),
    BAD_REQUEST("400"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500"),
    NOT_ALLOWED("405"),
    NO_DATA_FOUND("No Data Found");

    private final String code;
    HttpStatusCodes(String code) {
        this.code = code;
    }

}
