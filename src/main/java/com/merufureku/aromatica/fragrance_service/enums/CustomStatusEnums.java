package com.merufureku.aromatica.fragrance_service.enums;

import org.springframework.http.HttpStatus;

public enum CustomStatusEnums {

    NO_USER_FOUND(4000, "No User Found",HttpStatus.BAD_REQUEST),
    FRAGRANCE_NOT_FOUND(4040, "Perfume not found", HttpStatus.NOT_FOUND);

    private int statusCode;
    private String message;
    private HttpStatus httpStatus;

    CustomStatusEnums(int statusCode, String message, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
