package com.kirill.idfc.errors.controller;

import com.kirill.idfc.errors.AlreadyExistException;
import com.kirill.idfc.errors.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
@ResponseBody
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(AlreadyExistException ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), "Conflict with the current state of the resource", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorMessage> handleDMSRESTException(MethodArgumentNotValidException ex)
    {
        String error = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), "Validation failed", error);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}