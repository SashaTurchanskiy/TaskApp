package com.alekdev.TaskApp.exception;

import com.alekdev.TaskApp.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleAllUnknownException(Exception ex){
        Response<?> response = Response.builder()
                .statusCode(500)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException ex){
        Response<?> response = Response.builder()
                .statusCode(404)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<?>> handleBadRequestException(BadRequestException ex){
        Response<?> response = Response.builder()
                .statusCode(400)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(400).body(response);
    }
}
