package com.ms.tourist_app.application.exceptions;

import com.ms.tourist_app.adapter.base.Reason;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomHandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        Reason reason = new Reason();
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NOTFOUND", reason, null);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundException(BadRequestException ex) {
        Reason reason = new Reason();
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "BAD_REQUEST", reason, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(MethodArgumentNotValidException ex) {
        Reason reason = new Reason();
        List<String> reasonElement = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            reasonElement.add(fieldName + " " + errorMessage);
        });
        reason.setReason(reasonElement);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "BAD_REQUEST", reason, null);
    }
}