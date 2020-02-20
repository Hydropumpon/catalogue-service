package com.example.catalogue.catalogueservice.controller;

import com.example.catalogue.catalogueservice.exception.ConflictException;
import com.example.catalogue.catalogueservice.exception.ErrorMessage;
import com.example.catalogue.catalogueservice.exception.ErrorResponse;
import com.example.catalogue.catalogueservice.exception.ServiceErrorCode;
import com.example.catalogue.catalogueservice.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String UNIQUE_CONSTRAINT_VIOLATION_CODE = "23505";
    private static final String CATEGORY_NAME_UNIQUE_CONSTRAINT = "category_name_key";
    private static final String MANUFACTURER_NAME_UNIQUE_CONSTRAINT = "manufacturer_name_key";

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceExceptionHandler(ServiceException exception) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), exception.getErrorCode().getErrorCode(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> sqlViolationHandler(DataIntegrityViolationException exception) {
        Throwable rootCause = exception.getRootCause();
        SQLException sqlException = (SQLException) rootCause;
        String message = rootCause.getMessage();

        if (sqlException.getSQLState().equals(UNIQUE_CONSTRAINT_VIOLATION_CODE)) {
            if (message != null) {
                if (message.contains(CATEGORY_NAME_UNIQUE_CONSTRAINT)) {
                    return serviceExceptionHandler(
                            new ConflictException(ErrorMessage.CATEGORY_EXIST, ServiceErrorCode.ALREADY_EXIST));
                } else if (message.contains(MANUFACTURER_NAME_UNIQUE_CONSTRAINT)) {
                    return serviceExceptionHandler(
                            new ConflictException(ErrorMessage.MANUFACTURER_EXIST, ServiceErrorCode.ALREADY_EXIST));
                }
            }
        }
        return new ResponseEntity<>(
                new ErrorResponse(LocalDateTime.now(), ServiceErrorCode.INTERNAL_SERVER_ERROR.getErrorCode(), ""),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
