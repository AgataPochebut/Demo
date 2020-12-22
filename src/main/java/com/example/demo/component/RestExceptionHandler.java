package com.example.demo.component;

import com.example.demo.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SEMICOLON = ";";
    private static final String EMPTY = "";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage().concat(SEMICOLON))
                .reduce(EMPTY, String::concat);
        log.error(errorMessage, ex);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handle(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage().concat(SEMICOLON))
                .reduce(EMPTY, String::concat);
        log.error(errorMessage, exception);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handle(EntityNotFoundException exception) {
        String errorMessage = exception.getMessage();
        log.error(errorMessage, exception);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.NOT_FOUND, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

    @ExceptionHandler(value = {EntityExistsException.class})
    protected ResponseEntity<Object> handle(EntityExistsException exception) {
        String errorMessage = exception.getMessage();
        log.error(errorMessage, exception);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handle(Exception exception) {
        String errorMessage = exception.getMessage();
        log.error(errorMessage, exception);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handle(RuntimeException exception) {
        String errorMessage = exception.getMessage();
        log.error(errorMessage, exception);

        ErrorResponseDto responseDTO = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return ResponseEntity
                .status(responseDTO.getHttpStatus())
                .body(responseDTO);
    }

}
