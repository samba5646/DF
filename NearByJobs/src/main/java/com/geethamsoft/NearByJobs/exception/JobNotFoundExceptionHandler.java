package com.geethamsoft.NearByJobs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class JobNotFoundExceptionHandler {

    @ExceptionHandler(value = {JobNotFoundException.class})
    public ResponseEntity<Object> JobNotFoundException
            (JobNotFoundException jobNotFoundException)
    {
        JobException jobException = new JobException
                (jobNotFoundException.getMessage(),
                        jobNotFoundException.getCause(),
                        HttpStatus.NOT_FOUND
                );
        return new ResponseEntity<>(jobException,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        record Errors(String field, String message){};
        List<Errors> errors = ex.getBindingResult().getFieldErrors().stream().map((fe) ->{
            return new Errors(fe.getField(),fe.getDefaultMessage());
        }).toList();
        return ResponseEntity.ok(errors);    }
}
