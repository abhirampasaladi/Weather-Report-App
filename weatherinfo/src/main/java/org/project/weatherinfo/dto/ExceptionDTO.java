package org.project.weatherinfo.dto;

import java.util.Date;

public class ExceptionDTO {

    private String message;

    private Date date;

    public ExceptionDTO(String message, Date date) {
        this.message = message;
        this.date = date;
    }

}
