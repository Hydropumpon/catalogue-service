package com.example.catalogue.catalogueservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {
    public NotFoundException(String message, ServiceErrorCode errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }
}
