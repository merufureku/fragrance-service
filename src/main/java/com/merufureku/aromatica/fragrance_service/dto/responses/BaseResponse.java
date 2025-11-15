package com.merufureku.aromatica.fragrance_service.dto.responses;

public record BaseResponse<T>(int status, String message, T data){}
