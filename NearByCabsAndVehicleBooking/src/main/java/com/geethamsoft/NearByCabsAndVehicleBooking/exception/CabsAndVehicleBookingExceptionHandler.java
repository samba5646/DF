package com.geethamsoft.NearByCabsAndVehicleBooking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CabsAndVehicleBookingExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        record Errors(String field, String message){};
        List<Errors> errors = ex.getBindingResult().getFieldErrors().stream().map((fe) ->{
            return new Errors(fe.getField(),fe.getDefaultMessage());
        }).toList();
        return ResponseEntity.ok(errors);
    }
}
