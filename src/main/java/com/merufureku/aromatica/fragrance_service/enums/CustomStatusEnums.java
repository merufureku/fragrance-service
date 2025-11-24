package com.merufureku.aromatica.fragrance_service.enums;

import org.springframework.http.HttpStatus;

public enum CustomStatusEnums {

    NO_USER_FOUND(4000, "No User Found",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4001, "Invalid Token", HttpStatus.UNAUTHORIZED),
    FRAGRANCE_NOT_FOUND(4002, "Perfume not found", HttpStatus.NOT_FOUND),
    FRAGRANCE_ALREADY_EXIST(4003, "Perfume already exist", HttpStatus.CONFLICT),
    NOTE_ALREADY_EXIST(4040, "Note {} already exist", HttpStatus.CONFLICT),
    NO_NOTES_TO_INSERT(4041, "No notes to insert", HttpStatus.BAD_REQUEST),
    NOTE_NOT_EXIST(4042, "Note ID not found", HttpStatus.BAD_REQUEST);

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

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
