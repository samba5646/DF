package com.geethamsoft.NearbyBusinessIdeas.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class PostBusinessExceptionHandler {
    @ExceptionHandler(value = {PostBusinessNotFoundException.class})
        public ResponseEntity<Object> PostBusinessNotFoundException
                (PostBusinessNotFoundException postBusinessNotFoundException)
        {
            PostBusinessException businessException= new PostBusinessException
                    (postBusinessNotFoundException.getMessage(),
                            postBusinessNotFoundException.getCause(),
                            HttpStatus.NOT_FOUND

                    );
            return new ResponseEntity<>(businessException,HttpStatus.NOT_FOUND);
        }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})

    public ResponseEntity<Object>BusinessNotFoundException
            (MethodArgumentNotValidException  aa){

        List<FieldError> fieldErrors = aa.getBindingResult().getFieldErrors();
        List<Error> errors = fieldErrors.stream().map((e)->{
            return new Error(e.getDefaultMessage(),e.getField());
        }).toList();
        return new ResponseEntity<Object>(errors ,HttpStatus.BAD_REQUEST);    }
}



