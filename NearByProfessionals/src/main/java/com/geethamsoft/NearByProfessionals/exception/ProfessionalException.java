package com.geethamsoft.NearByProfessionals.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ProfessionalException {
    private final String message;
    private final Throwable trowable;
    private final HttpStatus httpStatus;

}
