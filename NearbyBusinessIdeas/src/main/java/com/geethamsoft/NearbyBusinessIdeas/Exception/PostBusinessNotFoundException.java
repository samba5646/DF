package com.geethamsoft.NearbyBusinessIdeas.Exception;

public class PostBusinessNotFoundException extends RuntimeException{
    public PostBusinessNotFoundException(String message){
        super(message);
    }
    public PostBusinessNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }
}
