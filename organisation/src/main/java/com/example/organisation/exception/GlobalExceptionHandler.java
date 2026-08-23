package com.example.organisation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.organisation.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex){    
        return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            new ApiResponse<>(
                false,
                ex.getMessage(),
                null
            )
        );
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExists(
        ResourceAlreadyExists ex
    ){
        return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new ApiResponse<>(
            false,
            ex.getMessage(),
            null
        ));

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(
        BadRequestException ex
    ){
        return ResponseEntity
            .badRequest()
            .body(
                new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
                )
            );
    }
}
