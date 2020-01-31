package com.example.catalogue.catalogueservice.exception;

import org.springframework.http.HttpStatus;

public class UnavailableException extends ServiceException {
    public UnavailableException(String message, ServiceErrorCode errorCode) {
        super(message, HttpStatus.IM_USED, errorCode);
    }
}
