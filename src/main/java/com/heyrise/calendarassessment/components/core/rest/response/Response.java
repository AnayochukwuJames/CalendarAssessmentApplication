package com.heyrise.calendarassessment.components.core.rest.response;

import lombok.Data;

@Data

public class Response<T> {
    private boolean status;
    private String message;
    private T data;

    public Response(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
