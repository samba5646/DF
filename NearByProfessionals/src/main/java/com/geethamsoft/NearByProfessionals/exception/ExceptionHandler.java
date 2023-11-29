package com.geethamsoft.NearByProfessionals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ProfessionalNotFoundException.class})
    public ResponseEntity<Object> ProfessionalNotFoundException
            (ProfessionalNotFoundException professionalNotFoundException)
    {
        ProfessionalException professionalException = new ProfessionalException
                (professionalNotFoundException.getMessage(),
                        professionalNotFoundException.getCause(),
                        HttpStatus.NOT_FOUND

                );
        return new ResponseEntity<>(professionalException,HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        record Errors(String field, String message){};
        List<Errors> errors = ex.getBindingResult().getFieldErrors().stream().map((fe) ->{
            return new Errors(fe.getField(),fe.getDefaultMessage());
        }).toList();
        return ResponseEntity.ok(errors);    }

}

