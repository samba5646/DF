package com.geethamsoft.NearByProfessionals.exception;

public class ProfessionalNotFoundException extends RuntimeException{
    public ProfessionalNotFoundException(String message) {
        super(message);
    }

    public ProfessionalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
